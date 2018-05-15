package org.garen.pitaya.service;

import org.apache.commons.collections.CollectionUtils;
import org.garen.pitaya.mybatis.domain.*;
import org.garen.pitaya.mybatis.service.SysPermissionService;
import org.garen.pitaya.redis.RedisService;
import org.garen.pitaya.util.JsonMapper;
import org.garen.pitaya.util.TransferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPermissionManage extends BaseManage<Long>{
    @Autowired
    SysPermissionService<SysPermission, SysPermissionQuery, Long> service;

    @Override
    public SysPermissionService<SysPermission, SysPermissionQuery, Long> getService() {
        return service;
    }

    @Autowired
    RedisService redisService;

    public boolean save(SysPermission sysPermission){
        int i = getService().save(sysPermission);
        if(i == 1){
            refreshTreeRedis();
            return true;
        }
        return false;
    }

    public int updateMulti(List<SysPermission> sysPermissions){
        int count = 0;
        for(SysPermission sysPermission : sysPermissions){
            count = count + getService().updateByKey(sysPermission);
        }
        // 更新缓存
        refreshTreeRedis();
        return count;
    }

    public int deleteMulti(String ids){
        List<SysPermissionDTO> listAll = getListAll();
        int count = 0;
        for(String idStr : ids.split(",")){
            count = count + delete(listAll, Long.parseLong(idStr));
        }
        // 更新缓存
        refreshTreeRedis();
        return count;
    }

    public SysPermission getByParentIdAndLabel(Long parentId, String label){
        SysPermissionQuery query = new SysPermissionQuery();
        SysPermissionQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        criteria.andLabelEqualTo(label);
        List<SysPermission> sysPermissionList = getService().findBy(query);
        if(sysPermissionList == null || sysPermissionList.size() == 0){
            return null;
        }
        return sysPermissionList.get(0);
    }

    public SysPermissionDTO getTree(){
        Object redisObj = redisService.get("sysPermissionDTOJson");
        if(redisObj != null){
            String json = (String) redisObj;
            SysPermissionDTO sysPermissionDTO = new JsonMapper().fromJson(json, SysPermissionDTO.class);
            return sysPermissionDTO;
        }
        return refreshTreeRedis();
    }

    public List<SysPermissionDTO> getListAll(){
        List<SysPermission> sysPermissionList = getService().findAll();
        List<SysPermissionDTO> sysPermissionDTOList = new ArrayList<>();
        for(SysPermission sysPermission : sysPermissionList){
            SysPermissionDTO sysPermissionDTO = new SysPermissionDTO();
            TransferUtil.transfer(sysPermissionDTO, sysPermission);
            sysPermissionDTOList.add(sysPermissionDTO);
        }
        return sysPermissionDTOList;
    }

    public List<SysPermissionDTO> getByParentId(List<SysPermissionDTO> all, Long parentId){
        List<SysPermissionDTO> children = new ArrayList<>();
        for(SysPermissionDTO sysPermissionDTO : all){
            if(sysPermissionDTO.getParentId() == parentId){
                children.add(sysPermissionDTO);
            }
        }
        return children;
    }

    public SysPermissionDTO getById(List<SysPermissionDTO> all, Long id){
        for(SysPermissionDTO sysPermissionDTO : all){
            if(sysPermissionDTO.getId() == id){
                return sysPermissionDTO;
            }
        }
        return null;
    }


    public void setChildren(List<SysPermissionDTO> all, List<SysPermissionDTO> children){
        if(!CollectionUtils.isEmpty(children)){
            for(SysPermissionDTO sysPermissionDTO : children){
                Long parentId = sysPermissionDTO.getId();
                List<SysPermissionDTO> children2 = getByParentId(all, parentId);
                sysPermissionDTO.setChildren(children2);
                setChildren(all, children2);
            }
        }
    }

    private int delete(List<SysPermissionDTO> all, Long id){
        SysPermissionDTO sysPermissionDTO = getById(all, id);
        List<Long> allChildrenIds = getAllChildrenIds(all, sysPermissionDTO.getChildren());
        allChildrenIds.add(sysPermissionDTO.getId());
        return deleteByIds(allChildrenIds);
    }

    private List<Long> getAllChildrenIds(List<SysPermissionDTO> all, List<SysPermissionDTO> children){
        List<Long> ids = new ArrayList<>();
        if(!CollectionUtils.isEmpty(children)){
            for(SysPermissionDTO sysPermissionDTO : children){
                ids.add(sysPermissionDTO.getId());
                Long parentId = sysPermissionDTO.getId();
                List<SysPermissionDTO> children2 = getByParentId(all, parentId);
                getAllChildrenIds(all, children);
            }
        }
        return ids;
    }

    private int deleteByIds(List<Long> ids){
        SysPermissionQuery query = new SysPermissionQuery();
        SysPermissionQuery.Criteria criteria = query.createCriteria();
        criteria.andIdIn(ids);
        return getService().delete(query);
    }


    private SysPermissionDTO refreshTreeRedis(){
        List<SysPermissionDTO> all = getListAll();
        SysPermissionDTO sysPermissionDTO = getById(all, 0L);
        List<SysPermissionDTO> children = getByParentId(all, 0L);
        sysPermissionDTO.setChildren(children);
        setChildren(all, children);
        String sysPermissionDTOJson = new JsonMapper().toJson(sysPermissionDTO);
        redisService.set("sysPermissionDTOJson", sysPermissionDTOJson);
        return sysPermissionDTO;
    }


}
