package org.garen.pitaya.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.garen.pitaya.mybatis.domain.*;
import org.garen.pitaya.mybatis.domain.SysRoleQuery;
import org.garen.pitaya.mybatis.service.SysRoleService;
import org.garen.pitaya.swagger.model.SysRoleSearch;
import org.garen.pitaya.swagger.model.SysRoleVo;
import org.garen.pitaya.util.EsapiUtil;
import org.garen.pitaya.util.TransferUtil;
import org.garen.pitaya.valid.SysRoleValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleManage extends BaseManage<Long>{
    @Autowired
    SysRoleService<SysRole, SysRoleQuery, Long> service;

    @Override
    public SysRoleService<SysRole, SysRoleQuery, Long> getService() {
        return service;
    }

    @Autowired
    SysRoleValid sysRoleValid;

    /**
     * 新增
     * @param sysRoleVo
     * @return
     */
    public boolean save(SysRoleVo sysRoleVo){
        // 校验入参
        sysRoleValid.saveValid(sysRoleVo);
        // 对象转化
        SysRole sysRole = new SysRole();
        TransferUtil.transfer(sysRole, sysRoleVo);
        // 持久化
        int i = create(sysRole);
        // 返回结果
        if(i == 1){
            return true;
        }
        return false;
    }

    /**
     * 修改
     * @param sysRoleVo
     * @return
     */
    public boolean update(SysRoleVo sysRoleVo){
        // 校验入参
        sysRoleValid.updateValid(sysRoleVo);
        // 对象转化
        SysRole sysRole = new SysRole();
        TransferUtil.transfer(sysRole, sysRoleVo);
        // 持久化
        int i = modify(sysRole);
        // 返回结果
        if(i == 1){
            return true;
        }
        return false;
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    public String batchRemove(String ids){
        int count = 0;
        List<String> failList = new ArrayList<>();
        for(String id : ids.split(",")){
            int i = removeById(Long.parseLong(id));
            if(i == 1){
                count ++;
            }else{
                SysRole byID = getService().findByID(Long.parseLong(id));
                failList.add(byID.getName());
            }
        }
        return failMsg(count, ids, failList);
    }
    

    public List<SysRole> getByProject(String project) {
        return getListByParams(null, null, project);
    }

    public List<SysRole> getByName(String name){
        return getListByParams(null, name, null);
    }

    public SysRole getByNameEqual(String name){
        SysRoleQuery query = new SysRoleQuery();
        SysRoleQuery.Criteria criteria = query.createCriteria();
        criteria.andNameEqualTo(name);
        List<SysRole> by = getService().findBy(query);
        if(!CollectionUtils.isEmpty(by)){
            return by.get(0);
        }
        return null;
    }

    /**
     * 通过编码查询
     * @param code
     * @return
     */
    public SysRole getByCode(String code){
        List<SysRole> listByParams = getListByParams(code, null, null);
        if(!CollectionUtils.isEmpty(listByParams)){
            return null;
        }
        return listByParams.get(0);
    }

    /**
     * 通过属性查询多个用户，组合查询方式“与”
     * @param code
     * @param name
     * @param project
     * @return
     */
    private List<SysRole> getListByParams(String code, String name, String project){
        SysRoleQuery query = new SysRoleQuery();
        SysRoleQuery.Criteria criteria = query.createCriteria();
        if(StringUtils.isNotBlank(code)){
            criteria.andCodeEqualTo(EsapiUtil.sql(code));
        }
        if(StringUtils.isNotBlank(name)){
            criteria.andNameLike("%"+ EsapiUtil.sql(name)+"%");
        }
        if(StringUtils.isNotBlank(project)){
            criteria.andProjectEqualTo(project);
        }
        return getService().findBy(query);
    }

    /**
     * 分页构建query
     * @param sysRoleSearch
     * @return
     */
    private SysRoleQuery buildQuery(SysRoleSearch sysRoleSearch){
        SysRoleQuery query = new SysRoleQuery();
        SysRoleQuery.Criteria criteria = query.createCriteria();
        if(sysRoleSearch != null){
            if(sysRoleSearch.getStart() == null){
                sysRoleSearch.setStart(0);
            }
            if(sysRoleSearch.getLength() == null){
                sysRoleSearch.setLength(5);
            }
            if(StringUtils.isNotBlank(sysRoleSearch.getCode())){
                criteria.andCodeEqualTo(sysRoleSearch.getCode().trim());
            }
            if(StringUtils.isNotBlank(sysRoleSearch.getName())){
                criteria.andNameLike("%"+sysRoleSearch.getName().trim()+"%");
            }
            if(StringUtils.isNotBlank(sysRoleSearch.getProject())){
                criteria.andProjectEqualTo(sysRoleSearch.getProject().trim());
            }
        }
        return query;
    }

    /**
     * 分页列表
     * @param sysRoleSearch
     * @return
     */
    public List<SysRole> getByPage(SysRoleSearch sysRoleSearch){
        SysRoleQuery query = buildQuery(sysRoleSearch);
        query.setOrderByClause("id desc");
        return getService().findBy(new RowBounds(sysRoleSearch.getStart(), sysRoleSearch.getLength()), query);
    }

    /**
     * 分页总数量
     * @param sysRoleSearch
     * @return
     */
    public int getPageCount(SysRoleSearch sysRoleSearch) {
        return getService().countByExample(buildQuery(sysRoleSearch));
    }
    
    
}
