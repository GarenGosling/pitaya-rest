package org.garen.pitaya.service;

import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.*;
import org.garen.pitaya.mybatis.service.TOrgService;
import org.garen.pitaya.mybatis.service.TPostService;
import org.garen.pitaya.redis.RedisService;
import org.garen.pitaya.swagger.model.TOrgVo;
import org.garen.pitaya.util.JsonMapper;
import org.garen.pitaya.util.TransferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TOrgManage extends BaseManage<Long>{
    @Autowired
    TOrgService<TOrg, TOrgQuery, Long> service;
    @Autowired
    RedisService redisService;
    @Autowired
    TPostManage tPostManage;
    @Autowired
    THrManage tHrManage;

    @Override
    public TOrgService<TOrg, TOrgQuery, Long> getService() {
        return service;
    }

    public final String ROOT_NODE = "0";

    public boolean save(TOrgVo tOrgVo){
        TOrg tOrg = new TOrg();
        TransferUtil.transfer(tOrg, tOrgVo);
        tOrg.setCreateTime(new Date());
        int i = getService().save(tOrg);
        if(i == 1){
            // 更新缓存
            refreshTreeRedis(getTreeByDB(ROOT_NODE));
            return true;
        }else{
            return false;
        }
    }

    public boolean update(TOrgVo tOrgVo){
        TOrgDTO before = getByCode(getListAll(), tOrgVo.getCode());
        String fullPathBefore = before.getFullPath();
        String fullNameBefore = before.getFullName();
        TOrg tOrg = new TOrg();
        TransferUtil.transfer(tOrg, tOrgVo);
        int i = getService().updateByKey(tOrg);
        if(i == 1){
            // 修改所有子节点
            String sql = "update sys_org set full_path = replace(full_path,"+ fullPathBefore +","+tOrg.getFullPath()+"), full_name = replace(full_name,"+ fullNameBefore +","+tOrg.getFullName()+") where full_path like '%"+fullPathBefore+"%'";
            getService().updateBySQL(sql);
            // 更新缓存
            refreshTreeRedis(getTreeByDB(ROOT_NODE));
            return true;
        }else{
            return false;
        }
    }

    public int deleteMulti(String codes){
        int count = 0;
        for(String code : codes.split(",")){
            count = count + delete(code);
        }
        // 更新缓存
        refreshTreeRedis(getTreeByDB(ROOT_NODE));
        return count;
    }

    private int delete(String code){
        TOrg byCode = getByCode(code);
        if(byCode == null){
            throw new BadRequestException("删除失败，原因：编码不存在");
        }
        String fullPath = byCode.getFullPath() + "/";
        TOrgQuery tOrgQuery = new TOrgQuery();
        TOrgQuery.Criteria criteria = tOrgQuery.createCriteria();
        criteria.andCodeEqualTo(code);
        int i = getService().delete(tOrgQuery);
        if(i != 1){
            throw new BadRequestException("删除失败");
        }
        String sql = "delete from t_org where full_path like '%"+fullPath+"%'";
        int j = getService().deleteBySQL(sql);
        return i + j;
    }

    public List<TOrgDTO> getListAll(){
        List<TOrg> tOrgList = getService().findAll();
        List<TOrgDTO> tOrgDTOList = new ArrayList<>();
        for(TOrg tOrg : tOrgList){
            TOrgDTO tOrgDTO = new TOrgDTO();
            TransferUtil.transfer(tOrgDTO, tOrg);
            tOrgDTOList.add(tOrgDTO);
        }
        return tOrgDTOList;
    }

    /**
     * 获取组织树，组织树包括：子组织、子岗位、子人员
     * @return
     */
    public TOrgDTO getTree(){
        Object redisObj = redisService.get("tOrgDTOJson");
        if(redisObj != null){
            String json = (String) redisObj;
            TOrgDTO tOrgDTO = new JsonMapper().fromJson(json, TOrgDTO.class);
            return tOrgDTO;
        }
        // 从数据库获取组织树
        TOrgDTO tOrgDTO = getTreeByDB("0");
        // 刷新缓存
        return refreshTreeRedis(tOrgDTO);
    }

    public List<TOrgDTO> getByParentCode(List<TOrgDTO> all, String parentCode){
        List<TOrgDTO> children = new ArrayList<>();
        for(TOrgDTO tOrgDTO : all){
            if(tOrgDTO.getParentCode().equals(parentCode)){
                children.add(tOrgDTO);
            }
        }
        return children;
    }

    public TOrgDTO getByCode(List<TOrgDTO> all, String code){
        for(TOrgDTO tOrgDTO : all){
            if(tOrgDTO.getCode().equals(code)){
                return tOrgDTO;
            }
        }
        return null;
    }

    public TOrg getByCode(String code){
        TOrgQuery query = new TOrgQuery();
        TOrgQuery.Criteria criteria = query.createCriteria();
        criteria.andCodeEqualTo(code);
        List<TOrg> tOrgList = getService().findBy(query);
        if(!CollectionUtils.isEmpty(tOrgList)){
            return tOrgList.get(0);
        }
        return null;
    }

    /**
     * 数据库获取树
     * @param orgCode
     * @return
     */
    public TOrgDTO getTreeByDB(String orgCode){
        // org
        List<TOrgDTO> all = getListAll();
        TOrgDTO tOrgDTO = getByCode(all, orgCode);
        List<TOrgDTO> children = getByParentCode(all, orgCode);
        tOrgDTO.setChildren(children);
        // post
        List<TPostDTO> postAll = tPostManage.getListAll();
        List<TPostDTO> postChildren = tPostManage.getByOrgCode(postAll, orgCode);
        tOrgDTO.setPostChildren(postChildren);
        // hr
        List<THrDTO> hrAll = tHrManage.getListAll();
        List<THrDTO> hrChildren = tHrManage.getByOrgCode(hrAll, orgCode);
        tOrgDTO.setHrChildren(hrChildren);
        // 递归设置子节点
        setChildren(children, all, postAll, hrAll);
        return tOrgDTO;
    }

    /**
     * 刷新redis
     * @param tOrgDTO
     */
    public TOrgDTO refreshTreeRedis(TOrgDTO tOrgDTO){
        String tOrgDTOJson = new JsonMapper().toJson(tOrgDTO);
        redisService.set("tOrgDTOJson", tOrgDTOJson);
        return tOrgDTO;
    }

    public void setChildren(List<TOrgDTO> children, List<TOrgDTO> all, List<TPostDTO> postAll, List<THrDTO> hrAll){
        if(!CollectionUtils.isEmpty(children)){
            for(TOrgDTO tOrgDTO : children){
                // org
                String parentCode = tOrgDTO.getCode();
                List<TOrgDTO> children2 = getByParentCode(all, parentCode);
                tOrgDTO.setChildren(children2);
                // post
                List<TPostDTO> postChildren = tPostManage.getByOrgCode(postAll, parentCode);
                tOrgDTO.setPostChildren(postChildren);
                // hr
                List<THrDTO> hrChildren = tHrManage.getByOrgCode(hrAll, parentCode);
                tOrgDTO.setHrChildren(hrChildren);
                // 递归设置子节点
                setChildren(children2, all, postAll, hrAll);
            }
        }
    }

    public TOrg getByParentCodeAndName(String parentCode, String name){
        TOrgQuery query = new TOrgQuery();
        TOrgQuery.Criteria criteria = query.createCriteria();
        criteria.andParentCodeEqualTo(parentCode);
        criteria.andNameEqualTo(name);
        List<TOrg> by = getService().findBy(query);
        if(!CollectionUtils.isEmpty(by)){
            return by.get(0);
        }
        return null;
    }
}
