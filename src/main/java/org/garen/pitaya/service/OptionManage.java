package org.garen.pitaya.service;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.garen.pitaya.mybatis.domain.SysDictionary;
import org.garen.pitaya.mybatis.domain.SysDictionaryDTO;
import org.garen.pitaya.mybatis.domain.SysDictionaryQuery;
import org.garen.pitaya.redis.RedisService;
import org.garen.pitaya.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.garen.pitaya.swagger.model.Option;

@Service
public class OptionManage {

    @Autowired
    SysDictionaryManage sysDictionaryManage;
    @Autowired
    RedisService redisService;

    public List<Option> getIcons(){
        SysDictionary icon = sysDictionaryManage.getByCode("ICON");
        if(icon == null){
            return null;
        }
        String sql = "select code as value, label from sys_dictionary where parent_id = " + icon.getId();
        List<Map<String, Object>> bySQL = sysDictionaryManage.getService().findBySQL(sql);
        List<Option> optionList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(bySQL)){
            for(Map<String, Object> map : bySQL){
                Option option = new Option();
                option.setValue((String) map.get("value"));
                option.setLabel((String) map.get("label"));
                optionList.add(option);
            }
        }
        return optionList;
    }


}
