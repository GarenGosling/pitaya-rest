package org.garen.pitaya.service;

import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.*;
import org.garen.pitaya.mybatis.domain.THrDTO;
import org.garen.pitaya.mybatis.service.THrService;
import org.garen.pitaya.redis.RedisService;
import org.garen.pitaya.swagger.model.THrVo;
import org.garen.pitaya.util.JsonMapper;
import org.garen.pitaya.util.TransferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class THrManage extends BaseManage<Long>{
    @Autowired
    THrService<THr, THrQuery, Long> service;
    @Autowired
    RedisService redisService;
    @Autowired
    TOrgManage tOrgManage;


    @Override
    public THrService<THr, THrQuery, Long> getService() {
        return service;
    }

    public boolean save(THrVo tHrVo){
        THr tHr = new THr();
        TransferUtil.transfer(tHr, tHrVo);
        tHr.setCreateTime(new Date());
        int i = getService().save(tHr);
        if(i == 1){
            // 更新缓存
            tOrgManage.refreshTreeRedis(tOrgManage.getTreeByDB(tOrgManage.ROOT_NODE));
            return true;
        }else{
            return false;
        }
    }

    public boolean update(THrVo tHrVo){
        THr tHr = new THr();
        TransferUtil.transfer(tHr, tHrVo);
        int i = getService().updateByKey(tHr);
        if(i == 1){
            // 更新缓存
            tOrgManage.refreshTreeRedis(tOrgManage.getTreeByDB(tOrgManage.ROOT_NODE));
            return true;
        }else{
            return false;
        }
    }

    public int deleteMulti(String empNos){
        int count = 0;
        for(String empNo : empNos.split(",")){
            count = count + delete(empNo);
        }
        // 更新缓存
        tOrgManage.refreshTreeRedis(tOrgManage.getTreeByDB(tOrgManage.ROOT_NODE));
        return count;
    }

    private int delete(String code){
        THrQuery tHrQuery = new THrQuery();
        THrQuery.Criteria criteria = tHrQuery.createCriteria();
        criteria.andCodeEqualTo(code);
        return getService().delete(tHrQuery);
    }

    public List<THrDTO> getListAll(){
        List<THr> THrList = getService().findAll();
        List<THrDTO> THrDTOList = new ArrayList<>();
        for(THr THr : THrList){
            THrDTO THrDTO = new THrDTO();
            TransferUtil.transfer(THrDTO, THr);
            THrDTOList.add(THrDTO);
        }
        return THrDTOList;
    }

    public List<THrDTO> getByOrgCode(List<THrDTO> all, String orgCode){
        List<THrDTO> tHrDTOList = new ArrayList<>();
        for(THrDTO tHrDTO : all){
            if(tHrDTO.getOrgCode().equals(orgCode)){
                tHrDTOList.add(tHrDTO);
            }
        }
        return tHrDTOList;
    }

    public THr getByCode(String code){
        THrQuery query = new THrQuery();
        THrQuery.Criteria criteria = query.createCriteria();
        criteria.andCodeEqualTo(code);
        List<THr> THrList = getService().findBy(query);
        if(!CollectionUtils.isEmpty(THrList)){
            return THrList.get(0);
        }
        return null;
    }
}
