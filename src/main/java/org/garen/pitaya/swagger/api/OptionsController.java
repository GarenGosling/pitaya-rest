package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import org.garen.pitaya.service.OptionManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.Option;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/option")
public class OptionsController extends BaseModel {
    @Autowired
    OptionManage optionManage;


    @ApiOperation(value = "ICON", notes = "ICON")
    @RequestMapping(value = "/icon", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getIcons(){
        List<Option> icons = optionManage.getIcons();
        return new ResponseEntity<ResponseModel>(successModel("查询", icons), HttpStatus.OK);
    }

}
