package org.garen.pitaya.valid;

import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.enums.FileType;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.exception.BusinessException;
import org.garen.pitaya.service.SysAreaManage;
import org.garen.pitaya.swagger.model.ImportExcelResult;
import org.garen.pitaya.swagger.model.SysAreaVo;
import org.garen.pitaya.util.FileHandler;
import org.garen.pitaya.util.IdNumValidUtil;
import org.garen.pitaya.util.PhoneValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SysAreaValid {
    @Autowired
    SysAreaManage sysAreaManage;

    /**
     * 验证：新增接口
     * @param sysArea
     */
    public void saveValid(SysAreaVo sysArea){
        if(sysArea == null){
            throw new BadRequestException("新增地区不能为空");
        }
        if(StringUtils.isBlank(sysArea.getName())){
            throw new BadRequestException("名称不能为空");
        }
        if(StringUtils.isBlank(sysArea.getType())){
            throw new BadRequestException("类型不能为空");
        }
        if(!"省份".equals(sysArea.getType()) && sysArea.getParentId() == null){
            throw new BadRequestException("上级不能为空");
        }
    }

    /**
     * 验证：编辑接口
     * @param sysArea
     */
    public void updateValid(SysAreaVo sysArea){
        if(sysArea == null){
            throw new BadRequestException("新增地区不能为空");
        }
        if(sysArea.getId() == null){
            throw new BadRequestException("ID不能为空");
        }
    }
}
