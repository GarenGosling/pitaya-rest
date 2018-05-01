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
     * 编码查询
     * @param code
     * @return
     */
    public SysArea getByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        return getSingleByParamsOr(code, null, null, null);
    }

    /**
     * 名称查询
     * @param name
     * @return
     */
    public List<SysArea> getByNameLike(String name){
        if(StringUtils.isBlank(name)){
            return null;
        }
        return getListByParamsOr(null, name, null,null);
    }

    /**
     * 父编码查询
     * @param parentCode
     * @return
     */
    public List<SysArea> getByParentCode(String parentCode){
        if(StringUtils.isBlank(parentCode)){
            return null;
        }
        return getListByParamsOr(null, null, parentCode, null);
    }


    /**
     * 类型查询
     * @param type
     * @return
     */
    public List<SysArea> getByType(String type){
        if(StringUtils.isBlank(type)){
            return null;
        }
        return getListByParamsOr(null, null,null, type);
    }

    /**
     * 通过属性查询单个用户，组合查询方式“或”
     * @param code
     * @param name
     * @param parentCode
     * @param type
     * @return
     */
    public SysArea getSingleByParamsOr(String code, String name, String parentCode, String type){
        List<SysArea> listByParamsOr = getListByParamsOr(code, name, parentCode, type);
        if(!CollectionUtils.isEmpty(listByParamsOr) && listByParamsOr.size() > 0){
            return listByParamsOr.get(0);
        }
        return null;
    }

    /**
     * 通过属性查询多个用户，组合查询方式“或”
     * @param code
     * @param name
     * @param parentCode
     * @param type
     * @return
     */
    public List<SysArea> getListByParamsOr(String code, String name, String parentCode, String type){
        SysAreaQuery query = new SysAreaQuery();
        SysAreaQuery.Criteria criteria = query.or();
        if(StringUtils.isNotBlank(code)){
            criteria.andCodeEqualTo(code);
        }
        if(StringUtils.isNotBlank(name)){
            criteria.andNameLike("%"+name.trim()+"%");
        }
        if(StringUtils.isNotBlank(parentCode)){
            criteria.andParentCodeEqualTo(parentCode);
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
            sysAreaExport.setCode((String) map.get("code"));
            sysAreaExport.setName((String) map.get("name"));
            sysAreaExport.setParentCode((String) map.get("parent_code"));
            sysAreaExport.setParentName((String) map.get("parent_name"));
            sysAreaExport.setFullName((String) map.get("full_name"));
            sysAreaExport.setFullPath((String) map.get("full_path"));
            sysAreaExport.setType((String) map.get("type"));
            list.add(sysAreaExport);
        }
        String fileName = "地区信息";
        String[] columnNames = {"编码", "名称", "上级编码", "上级名称", "全名称", "全路径", "类型"};
        poiHandler.export(fileName, columnNames, list, response);
    }

    private List<Map<String, Object>> getByParams(SysAreaSearch sysAreaSearch){
        if(sysAreaSearch.getStart() == null){
            sysAreaSearch.setStart(0);
        }
        String sql = buildSql(sysAreaSearch) + " order by full_name asc limit " + sysAreaSearch.getStart() + ",10000";
        return getService().findBySQL(sql);
    }

    private String buildSql(SysAreaSearch sysAreaSearch){
        StringBuilder sb = new StringBuilder();
        sb.append("select code, name, parent_code, parent_name, full_name, full_path, type from sys_area where 1=1 ");
        if(sysAreaSearch != null){
            if(StringUtils.isBlank(sysAreaSearch.getCode())){
                sb.append(" and code = '" + sysAreaSearch.getCode() + "'");
            }
            if(StringUtils.isBlank(sysAreaSearch.getName())){
                sb.append(" and parent_id = '" + sysAreaSearch.getName() + "'");
            }
            if(StringUtils.isNotBlank(sysAreaSearch.getParentCode())){
                sb.append(" and name = '" + EsapiUtil.sql(sysAreaSearch.getParentCode()) + "'");
            }
            if(StringUtils.isNotBlank(sysAreaSearch.getType())){
                sb.append(" and type = '" + sysAreaSearch.getType() + "'");
            }
        }
        return sb.toString();
    }


}
