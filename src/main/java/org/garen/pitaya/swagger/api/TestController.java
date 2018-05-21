package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import org.garen.pitaya.service.TestManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class TestController extends BaseModel {
    @Autowired
    TestManage testManage;

    @ApiOperation(value = "测试登录",notes = "测试登录")
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> test(){
        return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
    }


}
