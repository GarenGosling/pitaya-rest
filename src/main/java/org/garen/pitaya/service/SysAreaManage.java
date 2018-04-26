package org.garen.pitaya.service;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.SysArea;
import org.garen.pitaya.mybatis.domain.SysAreaQuery;
import org.garen.pitaya.mybatis.service.SysAreaService;
import org.garen.pitaya.swagger.model.ImportExcelResult;
import org.garen.pitaya.swagger.model.SysAreaExport;
import org.garen.pitaya.swagger.model.SysAreaSearch;
import org.garen.pitaya.swagger.model.SysAreaVo;
import org.garen.pitaya.util.*;
import org.garen.pitaya.valid.SysAreaValid;
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

    @Override
    public SysAreaService<SysArea, SysAreaQuery, Long> getService() {
        return service;
    }

    @Autowired
    SysAreaValid sysAreaValid;
    @Autowired
    POIHandler poiHandler;

    /**
     * 新增
     * @param sysArea
     * @return
     */
    public boolean save(SysAreaVo sysArea){
        // 校验入参
        sysAreaValid.saveValid(sysArea);
        // 对象转化
        SysArea dist = new SysArea();
        TransferUtil.transfer(dist, sysArea);
        // 持久化
        int i = create(dist);
        // 返回结果
        if(i == 1){
            return true;
        }
        return false;
    }

    /**
     * 修改
     * @param sysArea
     * @return
     */
    public boolean update(SysAreaVo sysArea){
        // 校验入参
        sysAreaValid.updateValid(sysArea);
        // 对象转化
        SysArea dist = new SysArea();
        TransferUtil.transfer(dist, sysArea);
        // 持久化
        int i = modify(dist);
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
                SysAreaVo byId = findById(Long.parseLong(id));
                failList.add(byId.getName());
            }
        }
        return failMsg(count, ids, failList);
    }

    /**
     * 分页构建query
     * @param sysAreaSearch
     * @return
     */
    private SysAreaQuery buildQuery(SysAreaSearch sysAreaSearch){
        SysAreaQuery query = new SysAreaQuery();
        SysAreaQuery.Criteria criteria = query.createCriteria();
        if(sysAreaSearch != null){
            if(sysAreaSearch.getStart() == null){
                sysAreaSearch.setStart(0);
            }
            if(sysAreaSearch.getLength() == null){
                sysAreaSearch.setLength(5);
            }
            if(sysAreaSearch.getParentId() != null){
                criteria.andParentIdEqualTo(sysAreaSearch.getParentId());
            }
            if(StringUtils.isNotBlank(sysAreaSearch.getName())){
                criteria.andNameEqualTo(sysAreaSearch.getName().trim());
            }
            if(sysAreaSearch.getIsParent() != null){
                criteria.andIsParentEqualTo(sysAreaSearch.getIsParent());
            }
            if(StringUtils.isNotBlank(sysAreaSearch.getType())){
                criteria.andTypeEqualTo(sysAreaSearch.getType());
            }
        }
        return query;
    }

    /**
     * 分页列表
     * @param sysAreaSearch
     * @return
     */
    public List<SysArea> getByPage(SysAreaSearch sysAreaSearch){
        SysAreaQuery query = buildQuery(sysAreaSearch);
        query.setOrderByClause("fullname asc");
        return getService().findBy(new RowBounds(sysAreaSearch.getStart(), sysAreaSearch.getLength()), query);
    }

    /**
     * 分页总数量
     * @param sysAreaSearch
     * @return
     */
    public int getPageCount(SysAreaSearch sysAreaSearch) {
        return getService().countByExample(buildQuery(sysAreaSearch));
    }

    /**
     * 父ID查询
     * @param parentId
     * @return
     */
    public SysArea getByParentId(Integer parentId){
        if(parentId == null){
            return null;
        }
        return getSingleByParamsOr(parentId, null, null, null);
    }

    /**
     * 名称查询
     * @param name
     * @return
     */
    public SysArea getByNameLike(String name){
        if(StringUtils.isBlank(name)){
            return null;
        }
        return getSingleByParamsOr(null, name, null, null);
    }

    /**
     * 是否为父菜单查询
     * @param isParent
     * @return
     */
    public SysArea getByIsParent(Integer isParent){
        if(isParent != null){
            return null;
        }
        return getSingleByParamsOr(null,null, isParent, null);
    }

    /**
     * 类型查询
     * @param type
     * @return
     */
    public SysArea getByType(String type){
        if(StringUtils.isBlank(type)){
            return null;
        }
        return getSingleByParamsOr(null,null, null, type);
    }
    /**
     * 通过属性查询单个用户，组合查询方式“或”
     * @param parentId
     * @param name
     * @param isParent
     * @return
     */
    public SysArea getSingleByParamsOr(Integer parentId, String name, Integer isParent, String type){
        List<SysArea> listByParamsOr = getListByParamsOr(parentId, name, isParent, type);
        if(!CollectionUtils.isEmpty(listByParamsOr) && listByParamsOr.size() > 0){
            return listByParamsOr.get(0);
        }
        return null;
    }

    /**
     * 通过属性查询多个用户，组合查询方式“或”
     * @param parentId
     * @param name
     * @param isParent
     * @return
     */
    public List<SysArea> getListByParamsOr(Integer parentId, String name, Integer isParent, String type){
        SysAreaQuery query = new SysAreaQuery();
        SysAreaQuery.Criteria criteria = query.or();
        if(parentId != null){
            criteria.andParentIdEqualTo(parentId);
        }
        if(StringUtils.isNotBlank(name)){
            criteria.andNameLike("%"+name.trim()+"%");
        }
        if(isParent != null){
            criteria.andIsParentEqualTo(isParent);
        }
        if(StringUtils.isNotBlank(type)){
            criteria.andTypeEqualTo(type);
        }
        return getService().findBy(query);
    }

    /**
     * 导出Excel
     * @param sysAreaSearch
     * @param response
     * @return
     */
    public void exportExcel(SysAreaSearch sysAreaSearch, HttpServletResponse response){
        List<Map<String, Object>> maps = getByParams(sysAreaSearch);
        List<SysAreaExport> list = new ArrayList<>();
        for(Map<String, Object> map : maps){
            SysAreaExport sysAreaExport = new SysAreaExport();
            sysAreaExport.setName((String) map.get("name"));
            sysAreaExport.setFullname((String) map.get("fullname"));
            sysAreaExport.setType((String) map.get("type"));
            list.add(sysAreaExport);
        }
        String fileName = "用户信息";
        String[] columnNames = {"类型（可选值：省份、城市、区县）", "名称", "上级名称（可选值：中国、河北省等）"};
        poiHandler.export(fileName, columnNames, list, response);
    }

    private List<Map<String, Object>> getByParams(SysAreaSearch sysAreaSearch){
        if(sysAreaSearch.getStart() == null){
            sysAreaSearch.setStart(0);
        }
        String sql = buildSql(sysAreaSearch) + " order by fullname asc limit " + sysAreaSearch.getStart() + ",10000";
        return getService().findBySQL(sql);
    }

    private String buildSql(SysAreaSearch sysAreaSearch){
        StringBuilder sb = new StringBuilder();
        sb.append("select type,fullname,name from sys_area where 1=1 ");
        if(sysAreaSearch != null){
            if(sysAreaSearch.getParentId() != null){
                sb.append(" and parent_id = '" + sysAreaSearch.getParentId() + "'");
            }
            if(StringUtils.isNotBlank(sysAreaSearch.getName())){
                sb.append(" and name = '" + EsapiUtil.sql(sysAreaSearch.getName()) + "'");
            }
            if(sysAreaSearch.getIsParent() != null){
                sb.append(" and is_parent = '" + sysAreaSearch.getIsParent() + "'");
            }
            if(StringUtils.isNotBlank(sysAreaSearch.getType())){
                sb.append(" and type = '" + sysAreaSearch.getType() + "'");
            }
        }
        return sb.toString();
    }

}
