package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.swagger.model.SuccessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class SysUserController extends BaseModel {
    @Autowired
    SysUserManage sysUserManage;

    @ApiOperation(value = "分页查询", nickname = "getOrderByPage", notes = "分页查询", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = ResponseModel.class),
            @ApiResponse(code = 200, message = "unexpected error", response = ResponseModel.class) })
    @RequestMapping(value = "/sysUsers",
            method = RequestMethod.GET)
    ResponseEntity<ResponseModel> getOrderByPage(@ApiParam(value = "分页开始索引") @Valid @RequestParam(value = "start", required = false) Integer start,
                                                @ApiParam(value = "每页数量") @Valid @RequestParam(value = "length", required = false) Integer length,
                                                @ApiParam(value = "订单号") @Valid @RequestParam(value = "orderNum", required = false) String orderNum){
        if(start == null){
            start = 0;
        }
        if(length == null){
            length = 10;
        }

        return new ResponseEntity<ResponseModel>(successModel("查询成功"), HttpStatus.OK);
    }

}
