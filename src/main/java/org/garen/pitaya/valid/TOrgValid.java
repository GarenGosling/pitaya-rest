package org.garen.pitaya.valid;

import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.SysArea;
import org.garen.pitaya.mybatis.domain.TOrg;
import org.garen.pitaya.mybatis.domain.TOrgDTO;
import org.garen.pitaya.service.SysAreaManage;
import org.garen.pitaya.service.TOrgManage;
import org.garen.pitaya.swagger.model.TOrgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TOrgValid {

    @Autowired
    TOrgManage tOrgManage;

    /**
     * 验证：新增接口
     * @param tOrgVo
     */
    public void saveValid(TOrgVo tOrgVo){
        // 非空验证：参数对象
        if(tOrgVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        // 非空验证：编码
        if(StringUtils.isBlank(tOrgVo.getCode())){
            throw new BadRequestException("节点编码不能为空");
        }
        // 非空验证：名称
        if(StringUtils.isBlank(tOrgVo.getName())){
            throw new BadRequestException("节点名称不能为空");
        }
        // 非空验证：父节点编码
        if(StringUtils.isBlank(tOrgVo.getParentCode())){
            throw new BadRequestException("父节点编码不能为空");
        }
        // 唯一性校验: 父节点必需存在
        if(!tOrgManage.ROOT_NODE.equals(tOrgVo.getParentCode())){
            TOrg byCode = tOrgManage.getByCode(tOrgVo.getParentCode());
            if(byCode == null){
                throw new BadRequestException("父节点不存在");
            }
        }
        // 唯一性校验： 父节点下不能有同名子节点
        TOrg byParentCodeAndName = tOrgManage.getByParentCodeAndName(tOrgVo.getParentCode(), tOrgVo.getName());
        if(byParentCodeAndName != null){
            throw new BadRequestException("相同父节点下不能有同名的子节点");
        }
    }

    /**
     * 验证：编辑接口
     * @param tOrgVo
     */
    public void updateValid(TOrgVo tOrgVo){
        // 参数对象
        if(tOrgVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        // 非空验证：编码
        if(StringUtils.isBlank(tOrgVo.getCode())){
            throw new BadRequestException("节点编码不能为空");
        }
        // 非空验证：编码必需存在
        TOrg byCode = tOrgManage.getByCode(tOrgVo.getCode());
        if(byCode == null){
            throw new BadRequestException("编码不存在");
        }
        // 唯一性校验: 父节点必需存在
        if(!tOrgManage.ROOT_NODE.equals(tOrgVo.getParentCode())){
            TOrg byParentCode = tOrgManage.getByCode(tOrgVo.getParentCode());
            if(byParentCode == null){
                throw new BadRequestException("父节点不存在");
            }
        }

        // 唯一性校验： 父节点下不能有同名子节点
        if(StringUtils.isNotBlank(tOrgVo.getName()) && !tOrgVo.getName().equals(byCode.getName())){
            String parentCode = null;
            if(StringUtils.isNotBlank(tOrgVo.getParentCode())){
                parentCode = tOrgVo.getParentCode();
            }else{
                parentCode = byCode.getParentCode();
            }
            TOrg byParentCodeAndName = tOrgManage.getByParentCodeAndName(parentCode, tOrgVo.getName());
            if(byParentCodeAndName != null){
                throw new BadRequestException("相同父节点下不能有同名的子节点");
            }
        }
    }

    /**
     * 验证：删除接口
     * @param code
     */
    public void deleteValid(String code){
        if(StringUtils.isBlank(code)){
            throw new BadRequestException("编码不能为空");
        }
    }

    /**
     * 验证：编码查询接口
     * @param code
     */
    public void getByCodeValid(String code){
        if(StringUtils.isBlank(code)){
            throw new BadRequestException("编码不能为空");
        }
    }

}
