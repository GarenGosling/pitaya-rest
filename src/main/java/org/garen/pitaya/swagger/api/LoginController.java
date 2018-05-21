package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.garen.pitaya.service.LoginManage;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.swagger.model.*;
import org.garen.pitaya.util.POIHandler;
import org.garen.pitaya.valid.SysUserValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController extends BaseModel {
    @Autowired
    LoginManage loginManage;

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> login(@ApiParam(value = "用户名") @Valid @RequestParam(value = "loginName", required = true) String loginName,
                                        @ApiParam(value = "密码") @Valid @RequestParam(value = "password", required = true) String password){
        LoginInfo loginInfo = loginManage.login(loginName, password);
        return new ResponseEntity<ResponseModel>(successModel("登录", loginInfo), HttpStatus.OK);
    }

    @ApiOperation(value = "退出", notes = "退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> logout(HttpServletRequest request){
        String ticket = request.getHeader("ticket");
        boolean logout = loginManage.logout(ticket);
        if(logout){
            return new ResponseEntity<ResponseModel>(successModel("退出"), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel("退出"), HttpStatus.OK);
        }
    }

}
