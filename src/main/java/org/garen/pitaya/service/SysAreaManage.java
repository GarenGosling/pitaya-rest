package org.garen.pitaya.service;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.SysArea;
import org.garen.pitaya.mybatis.domain.SysAreaDTO;
import org.garen.pitaya.mybatis.domain.SysAreaQuery;
import org.garen.pitaya.mybatis.service.SysAreaService;
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

    @Override
    public SysAreaService<SysArea, SysAreaQuery, Long> getService() {
        return service;
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
            sysAreaDTO.setIsLeaf(false);
            sysAreaDTO.setChildren(new ArrayList<>());
            sysAreaDTOList.add(sysAreaDTO);
        }
        return sysAreaDTOList;
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

}
