package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.garen.pitaya.code.IncrementCode;
import org.garen.pitaya.code.IncrementCodeHandler;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.swagger.model.SysUserSearch;
import org.garen.pitaya.swagger.model.SysUserVo;
import org.garen.pitaya.util.POIHandler;
import org.garen.pitaya.valid.SysUserValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
@Log
@RestController
@RequestMapping(value = "/code")
public class CodeController extends BaseModel {
    @Autowired
    IncrementCodeHandler incrementCodeHandler;

    @ApiOperation(value = "新增", notes = "新增 ")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> save(@RequestBody IncrementCode incrementCode){
        incrementCodeHandler.save(incrementCode);
        return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> delete(@ApiParam(value = "名称") @Valid @RequestParam(value = "name", required = true) String name){
        try {
            incrementCodeHandler.delete(name);
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        } catch (URISyntaxException e) {
            log.throwing(this.getClass().getName(), "delete", e);
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "查询", notes = "查询")
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> get(@ApiParam(value = "名称") @Valid @RequestParam(value = "name", required = true) String name){
        try {
            IncrementCode incrementCode = incrementCodeHandler.get(name);
            return new ResponseEntity<ResponseModel>(successModel(incrementCode), HttpStatus.OK);
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "get", e);
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "消费", notes = "消费")
    @RequestMapping(value = "/consume", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> consume(@ApiParam(value = "名称") @Valid @RequestParam(value = "name", required = true) String name){
        try {
            incrementCodeHandler.consume(name);
            return new ResponseEntity<ResponseModel>(successModel("消费"), HttpStatus.OK);
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "consume", e);
            return new ResponseEntity<ResponseModel>(badRequestModel("消费"), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "解锁", notes = "解锁")
    @RequestMapping(value = "/unLock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> unLock(@ApiParam(value = "名称") @Valid @RequestParam(value = "name", required = true) String name){
        try {
            incrementCodeHandler.unLock(name);
            return new ResponseEntity<ResponseModel>(successModel("解锁"), HttpStatus.OK);
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "consume", e);
            return new ResponseEntity<ResponseModel>(badRequestModel("解锁"), HttpStatus.OK);
        }
    }

}
