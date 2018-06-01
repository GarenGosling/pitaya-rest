package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.garen.pitaya.mybatis.domain.THr;
import org.garen.pitaya.mybatis.domain.THrDTO;
import org.garen.pitaya.service.THrManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.swagger.model.THrVo;
import org.garen.pitaya.util.TransferUtil;
import org.garen.pitaya.valid.THrValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/tHr")
public class THrController extends BaseModel {
    @Autowired
    THrValid tHrValid;
    @Autowired
    THrManage tHrManage;

    @ApiOperation(value = "新增", notes = "新增 ")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> save(@RequestBody THrVo tHrVo){
        tHrValid.saveValid(tHrVo);
        boolean save = tHrManage.save(tHrVo);
        if(save){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "编辑", notes = "编辑")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> update(@RequestBody THrVo tHrVo){
        tHrValid.updateValid(tHrVo);
        boolean update = tHrManage.update(tHrVo);
        if(update){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> delete(@ApiParam(value = "主键") @Valid @RequestParam(value = "ids", required = false) String ids){
        for(String id : ids.split(",")){
            tHrValid.deleteValid(id);
        }
        int i = tHrManage.deleteMulti(ids);
        return new ResponseEntity<ResponseModel>(successModel().data("删除节点数量："+i), HttpStatus.OK);
    }

    @ApiOperation(value = "ID查询", notes = "ID查询")
    @RequestMapping(value = "/getById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getById(@ApiParam(value = "编码查询") @Valid @RequestParam(value = "id", required = false) String id){
        tHrValid.getByIdValid(id);
        THr tHr = tHrManage.getById(id);
        THrDTO tHrDTO = new THrDTO();
        TransferUtil.transfer(tHrDTO, tHr);
        return new ResponseEntity<ResponseModel>(successModel("查询", tHrDTO), HttpStatus.OK);
    }
}
