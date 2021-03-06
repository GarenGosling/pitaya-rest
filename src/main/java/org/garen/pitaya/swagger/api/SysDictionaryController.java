package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.garen.pitaya.mybatis.domain.SysArea;
import org.garen.pitaya.mybatis.domain.SysAreaDTO;
import org.garen.pitaya.mybatis.domain.SysDictionary;
import org.garen.pitaya.mybatis.domain.SysDictionaryDTO;
import org.garen.pitaya.service.SysAreaManage;
import org.garen.pitaya.service.SysDictionaryManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.util.TransferUtil;
import org.garen.pitaya.valid.SysAreaValid;
import org.garen.pitaya.valid.SysDictionaryValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/sysDictionary")
public class SysDictionaryController extends BaseModel {
    @Autowired
    SysDictionaryValid sysDictionaryValid;
    @Autowired
    SysDictionaryManage sysDictionaryManage;

    @ApiOperation(value = "查询整棵树", notes = "查询整棵树")
    @RequestMapping(value = "/getTree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getTree(){
        SysDictionaryDTO sysDictionaryDTO = sysDictionaryManage.getTree();
        return new ResponseEntity<ResponseModel>(successModel("查询", sysDictionaryDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "新增", notes = "新增 ")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> save(@RequestBody SysDictionary sysDictionary){
        sysDictionaryValid.saveValid(sysDictionary);
        boolean save = sysDictionaryManage.save(sysDictionary);
        if(save){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "编辑", notes = "编辑")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> update(@RequestBody List<SysDictionary> sysDictionaryList){
        for(SysDictionary sysDictionary : sysDictionaryList){
            sysDictionaryValid.updateValid(sysDictionary);
        }
        int i = sysDictionaryManage.updateMulti(sysDictionaryList);
        return new ResponseEntity<ResponseModel>(successModel().data("修改节点数量："+i), HttpStatus.OK);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> delete(@ApiParam(value = "主键") @Valid @RequestParam(value = "ids", required = false) String ids){
        for(String idStr : ids.split(",")){
            sysDictionaryValid.deleteValid(Long.parseLong(idStr));
        }
        int i = sysDictionaryManage.deleteMulti(ids);
        return new ResponseEntity<ResponseModel>(successModel().data("删除节点数量："+i), HttpStatus.OK);
    }

    @ApiOperation(value = "ID查询", notes = "ID查询")
    @RequestMapping(value = "/getById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getById(@ApiParam(value = "ID查询") @Valid @RequestParam(value = "id", required = false) Long id){
        SysDictionary sysDictionary = sysDictionaryManage.findById(id);
        SysDictionaryDTO sysDictionaryDTO = new SysDictionaryDTO();
        TransferUtil.transfer(sysDictionaryDTO, sysDictionary);
        return new ResponseEntity<ResponseModel>(successModel("查询", sysDictionaryDTO), HttpStatus.OK);
    }
}
