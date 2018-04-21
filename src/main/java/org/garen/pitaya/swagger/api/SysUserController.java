package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.service.transfer.SysUserTransfer;
import org.garen.pitaya.swagger.api.valid.SysUserValid;
import org.garen.pitaya.swagger.model.*;
import org.garen.pitaya.util.IdNumValidUtil;
import org.garen.pitaya.util.PhoneValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
}
