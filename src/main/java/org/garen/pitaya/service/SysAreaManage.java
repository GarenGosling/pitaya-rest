package org.garen.pitaya.service;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.SysArea;
import org.garen.pitaya.mybatis.domain.SysAreaDTO;
import org.garen.pitaya.mybatis.domain.SysAreaQuery;
import org.garen.pitaya.mybatis.service.SysAreaService;
import org.garen.pitaya.redis.RedisService;
import org.garen.pitaya.swagger.model.ImportExcelResult;
import org.garen.pitaya.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysAreaManage extends BaseManage<Long>{
    @Autowired
    SysAreaService<SysArea, SysAreaQuery, Long> service;
    @Autowired
    RedisService redisService;

    @Override
    public SysAreaService<SysArea, SysAreaQuery, Long> getService() {
        return service;
    }

    public boolean save(SysArea sysArea){
        int i = getService().save(sysArea);
        if(i == 1){
            SysArea child = getByParentIdAndLabel(sysArea.getParentId(), sysArea.getLabel());
            SysArea parent = findById(sysArea.getParentId());
            String fullPath = (parent.getFullPath()==null?"":parent.getFullPath()) + "/" + child.getId();
            String fullName = (parent.getFullName()==null?"":parent.getFullName()) + "/" + child.getLabel();
            int level = parent.getLevel() + 1;
            child.setFullPath(fullPath);
            child.setFullName(fullName);
            child.setLevel(level);
            getService().updateByKey(child);
            // 更新缓存
            updateChildRedis(child);
            return true;
        }else{
            return false;
        }
    }

    public int update(SysArea sysArea){
        // 修改前
        SysArea before = findById(sysArea.getId());
        Long parentIdBefore = before.getParentId();
        String fullPathBefore = before.getFullPath()==null?"":before.getFullPath() + "/";
        String fullNameBefore = before.getFullName()==null?"":before.getFullName() + "/";
        int levelBefore = before.getLevel();
        // 修改
        SysArea parent = findById(sysArea.getParentId());
        String fullPath = parent.getFullPath()==null?"":parent.getFullPath() + "/" + sysArea.getId();
        String fullName = parent.getFullName()==null?"":parent.getFullName() + "/" + sysArea.getLabel();
        int level = parent.getLevel() + 1;
        sysArea.setFullPath(fullPath);
        sysArea.setFullName(fullName);
        sysArea.setLevel(level);
        int i = getService().updateByKey(sysArea);
        if(i != 1){
            throw new BadRequestException("修改失败");
        }
        // 修改后
        Long parentIdAfter = sysArea.getParentId();
        if(parentIdBefore == parentIdAfter){
            return i;
        }
        String fullPathAfter = fullPath + "/";
        String fullNameAfter = fullName + "/";
        int levelAfter = sysArea.getLevel();
        int levelDiff = levelAfter - levelBefore;
        // 修改所有子节点
        String sql = "update sys_area set full_path = replace(full_path,"+ fullPathBefore +","+fullPathAfter+"), full_name = replace(full_name,"+ fullNameBefore +","+fullNameAfter+"), level = (level + "+levelDiff+") where full_path like '%"+fullPathBefore+"%'";
        int j = getService().updateBySQL(sql);
        return j + i;
    }

    public int updateMulti(List<SysArea> sysAreaList){
        int count = 0;
        for(SysArea sysArea : sysAreaList){
            count = count + update(sysArea);
        }
        // 更新缓存
        updateRedisAll();
        return count;
    }

    public int delete(Long id){
        SysArea sysArea = findById(id);
        if(sysArea == null){
            throw new BadRequestException("删除失败，原因：id不存在");
        }
        String fullPath = sysArea.getFullPath() + "/";
        int i = removeById(id);
        if(i != 1){
            throw new BadRequestException("删除失败");
        }
        String sql = "delete from sys_area where full_path like '%"+fullPath+"%'";
        int j = getService().deleteBySQL(sql);
        return i + j;
    }

    public int deleteMulti(String ids){
        int count = 0;
        for(String idStr : ids.split(",")){
            count = count + delete(Long.parseLong(idStr));
        }
        // 更新缓存
        updateRedisAll();
        return count;
    }





    public List<SysAreaDTO> getByParentId(Long parentId){
        SysAreaQuery query = new SysAreaQuery();
        SysAreaQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<SysArea> sysAreaList = getService().findBy(query);
        if(sysAreaList == null || sysAreaList.size() == 0){
            return null;
        }
        List<SysAreaDTO> sysAreaDTOList = new ArrayList<>();
        for(SysArea sysArea : sysAreaList){
            SysAreaDTO sysAreaDTO = new SysAreaDTO();
            TransferUtil.transfer(sysAreaDTO, sysArea);
            sysAreaDTO.setChildren(new ArrayList<>());
            sysAreaDTOList.add(sysAreaDTO);
        }
        return sysAreaDTOList;
    }

    public List<Map<String, Object>> getOptionsByParentId(Long parentId){
        String sql = "select id as value, label from sys_area where parent_id = " + parentId;
        List<Map<String, Object>> bySQL = getService().findBySQL(sql);
        return bySQL;
    }

    public SysArea getByParentIdAndLabel(Long parentId, String label){
        SysAreaQuery query = new SysAreaQuery();
        SysAreaQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        criteria.andLabelEqualTo(label);
        List<SysArea> sysAreaList = getService().findBy(query);
        if(sysAreaList == null || sysAreaList.size() == 0){
            return null;
        }
        return sysAreaList.get(0);
    }

    public SysAreaDTO getAll(){
        Object redisObj = redisService.get("sysAreaDTOJson");
        if(redisObj != null){
            String json = (String) redisObj;
            SysAreaDTO sysAreaDTO = new JsonMapper().fromJson(json, SysAreaDTO.class);
            return sysAreaDTO;
        }
        SysAreaDTO sysAreaDTO = new SysAreaDTO();
        SysArea sysArea = findById(0L);
        TransferUtil.transfer(sysAreaDTO, sysArea);
        List<SysAreaDTO> children = getByParentId(0L);
        sysAreaDTO.setChildren(children);
        setChildren(children);
        String sysAreaDTOJson = new JsonMapper().toJson(sysAreaDTO);
        redisService.set("sysAreaDTOJson", sysAreaDTOJson);
        return sysAreaDTO;
    }

    public void updateRedisAll(){
        SysAreaDTO sysAreaDTO = new SysAreaDTO();
        SysArea sysArea = findById(0L);
        TransferUtil.transfer(sysAreaDTO, sysArea);
        List<SysAreaDTO> children = getByParentId(0L);
        sysAreaDTO.setChildren(children);
        setChildren(children);
        String sysAreaDTOJson = new JsonMapper().toJson(sysAreaDTO);
        redisService.set("sysAreaDTOJson", sysAreaDTOJson);
    }

    public void setChildren(List<SysAreaDTO> children){
        if(!CollectionUtils.isEmpty(children)){
            for(SysAreaDTO sysAreaDTO : children){
                Long parentId = sysAreaDTO.getId();
                List<SysAreaDTO> children2 = getByParentId(parentId);
                sysAreaDTO.setChildren(children2);
                setChildren(children2);
            }
        }
    }

    private void updateChildRedis(SysArea sysArea){
        SysAreaDTO newChild = new SysAreaDTO();
        TransferUtil.transfer(newChild, sysArea);
        SysAreaDTO sysAreaDTO = getAll();
        List<SysAreaDTO> children = sysAreaDTO.getChildren();
        updateChildRedisRecursion(children, newChild);
        String sysAreaDTOJson = new JsonMapper().toJson(sysAreaDTO);
        redisService.set("sysAreaDTOJson", sysAreaDTOJson);
    }

    private void updateChildRedisRecursion(List<SysAreaDTO> children, SysAreaDTO newChild){
        for(SysAreaDTO child : children){
            List<SysAreaDTO> children1 = child.getChildren();
            if(child.getId() == newChild.getParentId()){
                for(int i=0;i<children1.size();i++){
                    SysAreaDTO sat = children1.get(i);
                    if(sat.getId() == newChild.getId()){
                        children1.set(i,newChild);
                        break;
                    }
                }
                children1.add(newChild);
                break;
            }else{
                updateChildRedisRecursion(children1, newChild);
            }
        }
    }

}
