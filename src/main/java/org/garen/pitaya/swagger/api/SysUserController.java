package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.enums.FileType;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.exception.BusinessException;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.service.helper.FileHelper;
import org.garen.pitaya.service.helper.POIHandler;
import org.garen.pitaya.service.transfer.SysUserTransfer;
import org.garen.pitaya.swagger.api.valid.ImportExcelValidResponse;
import org.garen.pitaya.swagger.api.valid.SysUserValid;
import org.garen.pitaya.swagger.model.*;
import org.garen.pitaya.util.IdNumValidUtil;
import org.garen.pitaya.util.PhoneValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.awt.Color.black;
import static java.awt.Color.white;

@RestController
@RequestMapping(value = "/api/sysUser")
public class SysUserController extends BaseModel {
    @Autowired
    SysUserManage sysUserManage;
    @Autowired
    SysUserValid sysUserValid;
    @Autowired
    SysUserTransfer sysUserTransfer;
    @Autowired
    POIHandler poiHandler;

    @ApiOperation(value = "分页查询", nickname = "getByPage", notes = "分页查询", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "response", response = ResponseModel.class) })
    @RequestMapping(value = "/page",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<ResponseModel> getByPage(@ApiParam(value = "查询") @Valid @RequestBody SysUserSearch sysUserSearch){
        if(sysUserSearch == null){
            sysUserSearch = new SysUserSearch();
        }
        List<org.garen.pitaya.mybatis.domain.SysUser> list = sysUserManage.getByPage(sysUserSearch);
        int totalCount = sysUserManage.getPageCount(sysUserSearch);
        return new ResponseEntity<ResponseModel>(successModel("查询",page(list, totalCount)), HttpStatus.OK);
    }

    @ApiOperation(value = "新增", nickname = "save", notes = "新增 ", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "response", response = ResponseModel.class) })
    @RequestMapping(value = "/save",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<ResponseModel> save(@ApiParam(value = "新增") @Valid @RequestBody SysUser sysUser){
        sysUserValid.validSave(sysUser);
        org.garen.pitaya.mybatis.domain.SysUser dist = sysUserTransfer.saveMTD(sysUser);
        int i = sysUserManage.create(dist);
        if(i==1){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "编辑", nickname = "modify", notes = "编辑", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "response", response = ResponseModel.class) })
    @RequestMapping(value = "/modify",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<ResponseModel> modify(@ApiParam(value = "编辑") @Valid @RequestBody SysUser sysUser){
        sysUserValid.validModify(sysUser);
        org.garen.pitaya.mybatis.domain.SysUser dist = sysUserTransfer.modifyMTD(sysUser);
        sysUserManage.modify(dist);
        return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
    }

    @ApiOperation(value = "删除", nickname = "delete", notes = "删除", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "response", response = ResponseModel.class) })
    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE)
    ResponseEntity<ResponseModel> delete(@ApiParam(value = "主键") @Valid @RequestParam(value = "id", required = true) Long id){
        int i = sysUserManage.removeById(id);
        if(i == 1){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "批量删除", nickname = "delete", notes = "批量删除", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "response", response = ResponseModel.class) })
    @RequestMapping(value = "/remove",
            method = RequestMethod.DELETE)
    ResponseEntity<ResponseModel> remove(@ApiParam(value = "主键拼串") @Valid @RequestParam(value = "ids", required = true) String ids){
        int count = 0;
        List<String> failList = new ArrayList<>();
        for(String id : ids.split(",")){
            int i = sysUserManage.removeById(Long.parseLong(id));
            if(i == 1){
                count ++;
            }else{
                SysUser byId = sysUserManage.findById(Long.parseLong(id));
                failList.add(byId.getNickName());
            }
        }
        if(count == ids.split(",").length){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            String failMsg = "删除失败项：";
            for(int i=0;i<failList.size();i++){
                failMsg += failList.get(i);
                if(i<failList.size()-1){
                    failMsg += "，";
                }
            }
            return new ResponseEntity<ResponseModel>(badRequestModel(failMsg), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "文件上传", nickname = "importExcel", notes = "导入Excel格式的用户数据", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "response", response = ResponseModel.class) })
    @RequestMapping(value = "/importExcel",
            produces = { "application/json" },
            consumes = { "multipart/form-data" },
            method = RequestMethod.POST)
    ResponseEntity<ResponseModel> importExcel(@ApiParam(value = "excel文件") @RequestPart("file") MultipartFile file) {
        try {
            Map<String, List<ImportExcelValidResponse>> map = sysUserManage.importExcel(file);
            return new ResponseEntity<ResponseModel>(successModel(map), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ResponseModel>(badRequestModel(e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "导出Excel", notes = "导出Excel格式的用户数据 ", response = SuccessModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "response", response = ResponseModel.class) })
    @RequestMapping(value = "/exportExcel",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> exportExcel(@ApiParam(value = "进件编号") @RequestParam(value = "code", required = false) String code,
                                                     @ApiParam(value = "进件编号") @RequestParam(value = "nickName", required = false) String nickName,
                                                     @ApiParam(value = "进件编号") @RequestParam(value = "realName", required = false) String realName,
                                                     @ApiParam(value = "进件编号") @RequestParam(value = "phone", required = false) String phone,
                                                     HttpServletResponse response){
        try{
            List<Map<String, Object>> maps = sysUserManage.getByParams(code, nickName, realName, phone);
            List<SysUserExport> list = new ArrayList<>();
            for(Map<String, Object> map : maps){
                SysUserExport sysUserExport = sysUserTransfer.exportExcelETE(map);
                list.add(sysUserExport);
            }
            String fileName = "用户信息";
            String[] columnNames = {"用户名", "姓名（必填）", "手机号（必填）", "身份证号", "省份", "城市", "微信号", "QQ号", "邮箱", "角色"};
            poiHandler.export(fileName, columnNames, list, response);
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<ResponseModel>(badRequestModel(e.getMessage()), HttpStatus.OK);
        }
    }

}
