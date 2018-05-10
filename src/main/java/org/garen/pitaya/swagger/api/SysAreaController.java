package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.garen.pitaya.mybatis.domain.SysArea;
import org.garen.pitaya.mybatis.domain.SysAreaDTO;
import org.garen.pitaya.service.SysAreaManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.util.TransferUtil;
import org.garen.pitaya.valid.SysAreaValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/sysArea")
public class SysAreaController extends BaseModel {
    @Autowired
    SysAreaValid sysAreaValid;
    @Autowired
    SysAreaManage sysAreaManage;

    @ApiOperation(value = "新增", notes = "新增 ")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> save(@RequestBody SysArea sysArea){
        sysAreaValid.saveValid(sysArea);
        boolean save = sysAreaManage.save(sysArea);
        if(save){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "编辑", notes = "编辑")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> update(@RequestBody List<SysArea> sysAreaList){
        for(SysArea sysArea : sysAreaList){
            sysAreaValid.updateValid(sysArea);
        }
        int i = sysAreaManage.updateMulti(sysAreaList);
        return new ResponseEntity<ResponseModel>(successModel().data("修改节点数量："+i), HttpStatus.OK);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> delete(@ApiParam(value = "主键") @Valid @RequestParam(value = "ids", required = false) String ids){
        for(String idStr : ids.split(",")){
            sysAreaValid.deleteValid(Long.parseLong(idStr));
        }
        int i = sysAreaManage.deleteMulti(ids);
        return new ResponseEntity<ResponseModel>(successModel().data("删除节点数量："+i), HttpStatus.OK);
    }

    @ApiOperation(value = "父ID查询", notes = "父ID查询")
    @RequestMapping(value = "/getByParentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getByParentId(@ApiParam(value = "父ID查询") @Valid @RequestParam(value = "parentId", required = false) Long parentId){
        sysAreaValid.getByParentIdValid(parentId);
        List<SysAreaDTO> sysAreaDTOList = sysAreaManage.getByParentId(parentId);
        return new ResponseEntity<ResponseModel>(successModel("上级编码查询", sysAreaDTOList), HttpStatus.OK);
    }

    @ApiOperation(value = "父ID查询", notes = "父ID查询")
    @RequestMapping(value = "/getOptionsByParentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getOptionsByParentId(@ApiParam(value = "父ID查询") @Valid @RequestParam(value = "parentId", required = false) Long parentId){
        sysAreaValid.getByParentIdValid(parentId);
        List<Map<String, Object>> list = sysAreaManage.getOptionsByParentId(parentId);
        return new ResponseEntity<ResponseModel>(successModel("上级编码查询", list), HttpStatus.OK);
    }

    @ApiOperation(value = "ID查询", notes = "ID查询")
    @RequestMapping(value = "/getById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getById(@ApiParam(value = "ID查询") @Valid @RequestParam(value = "id", required = false) Long id){
        SysArea sysArea = sysAreaManage.findById(id);
        SysAreaDTO sysAreaDTO = new SysAreaDTO();
        TransferUtil.transfer(sysAreaDTO, sysArea);
        return new ResponseEntity<ResponseModel>(successModel("查询", sysAreaDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "初始化查询", notes = "初始化查询")
    @RequestMapping(value = "/getInit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getInit(){
        Long rootId = 0L;
        SysArea sysAreaRoot = sysAreaManage.findById(rootId);
        SysAreaDTO rootDTO = new SysAreaDTO();
        TransferUtil.transfer(rootDTO, sysAreaRoot);
        List<SysAreaDTO> sysAreaDTOList = sysAreaManage.getByParentId(rootId);
        rootDTO.setChildren(sysAreaDTOList);
        return new ResponseEntity<ResponseModel>(successModel("初始化查询", rootDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getAll(){
        SysAreaDTO sysAreaDTO = sysAreaManage.getAll();
        return new ResponseEntity<ResponseModel>(successModel("查询", sysAreaDTO), HttpStatus.OK);
    }

}
