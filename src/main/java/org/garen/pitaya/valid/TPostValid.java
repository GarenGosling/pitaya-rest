package org.garen.pitaya.valid;

import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.TOrg;
import org.garen.pitaya.mybatis.domain.TPost;
import org.garen.pitaya.service.TOrgManage;
import org.garen.pitaya.service.TPostManage;
import org.garen.pitaya.swagger.model.TPostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TPostValid {

    @Autowired
    TOrgManage tOrgManage;
    @Autowired
    TPostManage tPostManage;


    /**
     * 验证：新增接口
     * @param tPostVo
     */
    public void saveValid(TPostVo tPostVo){
        // 非空验证：参数对象
        if(tPostVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        // 非空验证：编码
        if(StringUtils.isBlank(tPostVo.getCode())){
            throw new BadRequestException("节点编码不能为空");
        }
        // 非空验证：名称
        if(StringUtils.isBlank(tPostVo.getName())){
            throw new BadRequestException("节点名称不能为空");
        }
        // 非空验证：组织编码
        if(StringUtils.isBlank(tPostVo.getOrgCode())){
            throw new BadRequestException("组织编码不能为空");
        }
        // 唯一性校验: 组织编码必需存在
        if(!tOrgManage.ROOT_NODE.equals(tPostVo.getOrgCode())){
            TOrg tOrg = tOrgManage.getByCode(tPostVo.getOrgCode());
            if(tOrg == null){
                throw new BadRequestException("组织编码不存在");
            }
        }
        // 唯一性校验： 相同组织下不能有同名岗位
        TPost byOrgCodeAndName = tPostManage.getByOrgCodeAndName(tPostVo.getOrgCode(), tPostVo.getName());
        if(byOrgCodeAndName != null){
            throw new BadRequestException("相同组织下不能有同名的岗位");
        }
    }

    /**
     * 验证：编辑接口
     * @param tPostVo
     */
    public void updateValid(TPostVo tPostVo){
        // 参数对象
        if(tPostVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        // 非空验证：编码
        if(StringUtils.isBlank(tPostVo.getCode())){
            throw new BadRequestException("节点编码不能为空");
        }
        // 非空验证：编码必需存在
        TPost byCode = tPostManage.getByCode(tPostVo.getCode());
        if(byCode == null){
            throw new BadRequestException("编码不存在");
        }
        // 唯一性校验: 组织编码必需存在
        if(!tOrgManage.ROOT_NODE.equals(tPostVo.getOrgCode())){
            TOrg tOrg = tOrgManage.getByCode(tPostVo.getOrgCode());
            if(tOrg == null){
                throw new BadRequestException("组织编码不存在");
            }
        }

        // 唯一性校验： 相同组织下不能有同名岗位
        if(StringUtils.isNotBlank(tPostVo.getName()) && !tPostVo.getName().equals(byCode.getName())){
            String parentCode = null;
            if(StringUtils.isNotBlank(tPostVo.getOrgCode())){
                parentCode = tPostVo.getOrgCode();
            }else{
                parentCode = byCode.getOrgCode();
            }
            TPost byOrgCodeAndName = tPostManage.getByOrgCodeAndName(tPostVo.getOrgCode(), tPostVo.getName());
            if(byOrgCodeAndName != null){
                throw new BadRequestException("相同组织下不能有同名的岗位");
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
