package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.swagger.api.valid.SysUserValid;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.swagger.model.SuccessModel;
import org.garen.pitaya.swagger.model.SysUser;
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

    @ApiOperation(value = "分页查询", nickname = "getOrderByPage", notes = "分页查询", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "response", response = ResponseModel.class)})
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    ResponseEntity<ResponseModel> getOrderByPage(@ApiParam(value = "分页开始索引") @Valid @RequestParam(value = "start", required = false) Integer start,
                                                 @ApiParam(value = "每页数量") @Valid @RequestParam(value = "length", required = false) Integer length,
                                                 @ApiParam(value = "用户编码") @Valid @RequestParam(value = "code", required = false) String code,
                                                 @ApiParam(value = "昵称") @Valid @RequestParam(value = "nickName", required = false) String nickName,
                                                 @ApiParam(value = "姓名") @Valid @RequestParam(value = "realName", required = false) String realName,
                                                 @ApiParam(value = "手机号") @Valid @RequestParam(value = "phone", required = false) String phone){

        List<org.garen.pitaya.mybatis.domain.SysUser> list = sysUserManage.getByPage(start, length, code, nickName, realName, phone);
        int totalCount = sysUserManage.getPageCount(code, nickName, realName, phone);
        return new ResponseEntity<ResponseModel>(successModel("查询成功",page(list, totalCount)), HttpStatus.OK);
    }

    @ApiOperation(value = "新增", nickname = "save", notes = "新增 ", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = ResponseModel.class) })
    @RequestMapping(value = "/save",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<ResponseModel> save(@ApiParam(value = "新增") @Valid @RequestBody SysUser sysUser){
        sysUserValid.validSave(sysUser);
        boolean save = sysUserManage.save(sysUser);
        if(save){
            return new ResponseEntity<ResponseModel>(successModel("新增用户成功"), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(successModel("新增用户失败"), HttpStatus.OK);
        }
    }



}
