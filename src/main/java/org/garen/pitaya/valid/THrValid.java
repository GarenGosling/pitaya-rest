package org.garen.pitaya.valid;

import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.THr;
import org.garen.pitaya.mybatis.domain.TOrg;
import org.garen.pitaya.mybatis.domain.TPost;
import org.garen.pitaya.service.THrManage;
import org.garen.pitaya.service.TOrgManage;
import org.garen.pitaya.service.TPostManage;
import org.garen.pitaya.swagger.model.THrVo;
import org.garen.pitaya.swagger.model.TPostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class THrValid {

    @Autowired
    TOrgManage tOrgManage;
    @Autowired
    TPostManage tPostManage;
    @Autowired
    THrManage tHrManage;


    /**
     * 验证：新增接口
     * @param tHrVo
     */
    public void saveValid(THrVo tHrVo){
        // 非空验证：参数对象
        if(tHrVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        // 非空验证：编码
        if(StringUtils.isBlank(tHrVo.getCode())){
            throw new BadRequestException("节点编码不能为空");
        }
        // 非空验证：名称
        if(StringUtils.isBlank(tHrVo.getName())){
            throw new BadRequestException("节点名称不能为空");
        }
        // 非空验证：组织编码
        if(StringUtils.isBlank(tHrVo.getOrgCode())){
            throw new BadRequestException("组织编码不能为空");
        }
        // 唯一性校验: 组织编码必需存在
        if(!tOrgManage.ROOT_NODE.equals(tHrVo.getOrgCode())){
            TOrg tOrg = tOrgManage.getByCode(tHrVo.getOrgCode());
            if(tOrg == null){
                throw new BadRequestException("组织编码不存在");
            }
        }
    }

    /**
     * 验证：编辑接口
     * @param tHrVo
     */
    public void updateValid(THrVo tHrVo){
        // 参数对象
        if(tHrVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        // 非空验证：编码
        if(StringUtils.isBlank(tHrVo.getCode())){
            throw new BadRequestException("节点编码不能为空");
        }
        // 非空验证：编码必需存在
        THr byCode = tHrManage.getByCode(tHrVo.getCode());
        if(byCode == null){
            throw new BadRequestException("编码不存在");
        }
        // 唯一性校验: 组织编码必需存在
        if(!tOrgManage.ROOT_NODE.equals(tHrVo.getOrgCode())){
            TOrg tOrg = tOrgManage.getByCode(tHrVo.getOrgCode());
            if(tOrg == null){
                throw new BadRequestException("组织编码不存在");
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
