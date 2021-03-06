package org.garen.pitaya.valid;

import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.enums.FileType;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.exception.BusinessException;
import org.garen.pitaya.mybatis.domain.SysArea;
import org.garen.pitaya.service.SysAreaManage;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.swagger.model.ImportExcelResult;
import org.garen.pitaya.swagger.model.SysUserVo;
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
    public void saveValid(SysArea sysArea){
        // 非空验证：参数对象
        if(sysArea == null){
            throw new BadRequestException("参数对象不能为空");
        }
        // 非空验证：节点名称
        if(StringUtils.isBlank(sysArea.getLabel())){
            throw new BadRequestException("节点名称不能为空");
        }
        // 非空验证：父节点
        if(sysArea.getParentId() == null){
            throw new BadRequestException("父节点不能为空");
        }
        // 唯一性校验: 父节点必需存在
        if(sysArea.getParentId() != -1){
            SysArea byParentId = sysAreaManage.findById(sysArea.getParentId());
            if(byParentId == null){
                throw new BadRequestException("父节点不存在");
            }
        }
        // 唯一性校验： 父节点下不能有同名子节点
        SysArea byParentIdAndLabel = sysAreaManage.getByParentIdAndLabel(sysArea.getParentId(), sysArea.getLabel());
        if(byParentIdAndLabel != null){
            throw new BadRequestException("相同父节点下不能有同名的子节点");
        }
    }

    /**
     * 验证：编辑接口
     * @param sysArea
     */
    public void updateValid(SysArea sysArea){
        // 参数对象
        if(sysArea == null){
            throw new BadRequestException("参数对象不能为空");
        }
        // 非空验证：ID不能为空
        if(sysArea.getId() == null){
            throw new BadRequestException("ID不能为空");
        }
        // 非空验证：ID必需存在
        SysArea byId = sysAreaManage.findById(sysArea.getId());
        if(byId == null){
            throw new BadRequestException("ID不存在");
        }
        // 唯一性校验: 父节点必需存在
        if(sysArea.getParentId() != -1){
            SysArea byParentId = sysAreaManage.findById(sysArea.getParentId());
            if(byParentId == null){
                throw new BadRequestException("父节点不存在");
            }
        }

        // 唯一性校验： 父节点下不能有同名子节点
        if(StringUtils.isNotBlank(sysArea.getLabel()) && !sysArea.getLabel().equals(byId.getLabel())){
            Long parentId = null;
            if(sysArea.getParentId() != null){
                parentId = sysArea.getParentId();
            }else{
                parentId = byId.getParentId();
            }
            SysArea byParentIdAndLabel = sysAreaManage.getByParentIdAndLabel(parentId, sysArea.getLabel());
            if(byParentIdAndLabel != null){
                throw new BadRequestException("相同父节点下不能有同名的子节点");
            }
        }
    }

    /**
     * 验证：删除接口
     * @param id
     */
    public void deleteValid(Long id){
        if(id == null){
            throw new BadRequestException("ID不能为空");
        }
    }

    /**
     * 验证： 父ID查询接口
     * @param parentId
     */
    public void getByParentIdValid(Long parentId){
        if(parentId == null){
            throw new BadRequestException("父ID不能为空");
        }
    }
}
