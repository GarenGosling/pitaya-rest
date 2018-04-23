package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.util.POIHandler;
import org.garen.pitaya.transfer.SysUserTransfer;
import org.garen.pitaya.swagger.api.valid.ImportExcelValidResponse;
import org.garen.pitaya.swagger.api.valid.SysUserValid;
import org.garen.pitaya.swagger.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getByPage(SysUserSearch sysUserSearch){
        if(sysUserSearch == null){
            sysUserSearch = new SysUserSearch();
        }
        List<org.garen.pitaya.mybatis.domain.SysUser> list = sysUserManage.getByPage(sysUserSearch);
        int totalCount = sysUserManage.getPageCount(sysUserSearch);
        return new ResponseEntity<ResponseModel>(successModel("查询",page(list, totalCount)), HttpStatus.OK);
    }

    @ApiOperation(value = "新增", notes = "新增 ")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> save(@RequestBody SysUser sysUser){
        sysUserValid.validSave(sysUser);
        org.garen.pitaya.mybatis.domain.SysUser dist = sysUserTransfer.saveMTD(sysUser);
        int i = sysUserManage.create(dist);
        if(i==1){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "编辑", notes = "编辑")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> modify(@RequestBody SysUser sysUser){
        sysUserValid.validModify(sysUser);
        org.garen.pitaya.mybatis.domain.SysUser dist = sysUserTransfer.modifyMTD(sysUser);
        sysUserManage.modify(dist);
        return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> delete(@ApiParam(value = "主键") @Valid @RequestParam(value = "id", required = true) Long id){
        int i = sysUserManage.removeById(id);
        if(i == 1){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "批量删除",notes = "批量删除")
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @ApiOperation(value = "导入", notes = "导入Excel格式的用户数据")
    @RequestMapping(value = "/importExcel",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> importExcel(@RequestPart("file") MultipartFile file) {
        try {
            Map<String, List<ImportExcelValidResponse>> map = sysUserManage.importExcel(file);
            return new ResponseEntity<ResponseModel>(successModel(map), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ResponseModel>(badRequestModel(e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "导出Excel", notes = "导出Excel格式的用户数据 ")
    @RequestMapping(value = "/exportExcel",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> exportExcel(SysUserSearch sysUserSearch, HttpServletResponse response){
        try{
            List<Map<String, Object>> maps = sysUserManage.getByParams(sysUserSearch.getCode(), sysUserSearch.getNickName(), sysUserSearch.getRealName(), sysUserSearch.getPhone());
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
