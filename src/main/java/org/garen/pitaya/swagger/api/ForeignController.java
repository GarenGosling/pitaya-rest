package org.garen.pitaya.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.garen.pitaya.mybatis.domain.TOrg;
import org.garen.pitaya.mybatis.domain.TOrgDTO;
import org.garen.pitaya.service.TOrgManage;
import org.garen.pitaya.swagger.model.BaseModel;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.swagger.model.TOrgVo;
import org.garen.pitaya.util.TransferUtil;
import org.garen.pitaya.valid.TOrgValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/foreign")
public class ForeignController extends BaseModel {
    @Autowired
    TOrgValid tOrgValid;
    @Autowired
    TOrgManage tOrgManage;

    @ApiOperation(value = "刷新整棵树的缓存", notes = "刷新整棵树的缓存")
    @RequestMapping(value = "/tOrg/refreshTree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> refreshTree(){
        TOrgDTO tree = tOrgManage.refreshTreeRedis(tOrgManage.getTreeByDB(tOrgManage.ROOT_NODE));
        return new ResponseEntity<ResponseModel>(successModel("查询", tree), HttpStatus.OK);
    }

}
