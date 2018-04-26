package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.garen.pitaya.service.SysAreaManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.swagger.model.SysAreaSearch;
import org.garen.pitaya.swagger.model.SysAreaVo;
import org.garen.pitaya.util.POIHandler;
import org.garen.pitaya.valid.SysAreaValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/sysArea")
public class SysAreaController extends BaseModel {
    @Autowired
    SysAreaManage sysAreaManage;
    @Autowired
    SysAreaValid sysAreaValid;
    @Autowired
    POIHandler poiHandler;

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getByPage(SysAreaSearch sysAreaSearch){
        List<org.garen.pitaya.mybatis.domain.SysArea> list = sysAreaManage.getByPage(sysAreaSearch);
        int totalCount = sysAreaManage.getPageCount(sysAreaSearch);
        return new ResponseEntity<ResponseModel>(successModel("查询",page(list, totalCount)), HttpStatus.OK);
    }

    @ApiOperation(value = "新增", notes = "新增 ")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> save(@RequestBody SysAreaVo sysArea){
        if(sysAreaManage.save(sysArea)){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "编辑", notes = "编辑")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> update(@RequestBody SysAreaVo sysArea){
        if(sysAreaManage.update(sysArea)){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> delete(@ApiParam(value = "主键") @Valid @RequestParam(value = "id", required = true) Long id){
        if(sysAreaManage.removeById(id) == 1){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "批量删除",notes = "批量删除")
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> remove(@ApiParam(value = "主键拼串") @Valid @RequestParam(value = "ids", required = true) String ids){
        String failMsg = sysAreaManage.batchRemove(ids);
        if(StringUtils.isBlank(failMsg)){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(failMsg), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "导出Excel", notes = "导出Excel格式的用户数据 ")
    @RequestMapping(value = "/exportExcel",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void exportExcel(SysAreaSearch sysAreaSearch, HttpServletResponse response){
        sysAreaManage.exportExcel(sysAreaSearch, response);
    }

}
