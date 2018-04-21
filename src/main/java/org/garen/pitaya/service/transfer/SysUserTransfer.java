package org.garen.pitaya.service.transfer;

import org.apache.commons.lang.StringUtils;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.service.helper.SysUserHelper;
import org.garen.pitaya.swagger.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 转换辅助类
 *
 * @author Garen Gosling
 * @create 2018-04-21 12:20
 * @since v1.0
 */
@Component
public class SysUserTransfer {

    @Autowired
    SysUserManage sysUserManage;
    @Autowired
    SysUserHelper sysUserHelper;

    /**
     * 新增接口     Model -> DAO
     * @param sysUser
     * @return
     */
    public org.garen.pitaya.mybatis.domain.SysUser saveMTD(SysUser sysUser){
        org.garen.pitaya.mybatis.domain.SysUser dist = new org.garen.pitaya.mybatis.domain.SysUser();
        dist.setCode(sysUserHelper.createUserCode());
        dist.setNickName(sysUserHelper.createNickName(sysUser.getNickName()));
        dist.setRealName(sysUser.getRealName());
        dist.setPassword(sysUserHelper.encodePassword("111"));
        dist.setPhone(sysUser.getPhone());
        dist.setIdNumber(sysUser.getIdNumber());
        dist.setProvince(sysUser.getProvince());
        dist.setCity(sysUser.getCity());
        dist.setWechat(sysUser.getWechat());
        dist.setQq(sysUser.getQq());
        dist.setEmail(sysUser.getEmail());
        dist.setRoles(sysUser.getRoles());
        dist.setCreateTime(new Date());
        return dist;
    }

    /**
     * 修改接口    Model -> DAO
     * @param arg
     * @return
     */
    public org.garen.pitaya.mybatis.domain.SysUser modifyMTD(SysUser arg){
        org.garen.pitaya.mybatis.domain.SysUser dist = sysUserManage.getByCode(arg.getCode());
        if(StringUtils.isNotBlank(arg.getNickName())){
            dist.setNickName(arg.getNickName());
        }
        if(StringUtils.isNotBlank(arg.getRealName())){
            dist.setRealName(arg.getRealName());
        }
        if(StringUtils.isNotBlank(arg.getPhone())){
            dist.setPhone(arg.getPhone());
        }
        if(StringUtils.isNotBlank(arg.getIdNumber())){
            dist.setIdNumber(arg.getIdNumber());
        }
        if(StringUtils.isNotBlank(arg.getProvince())){
            dist.setProvince(arg.getProvince());
        }
        if(StringUtils.isNotBlank(arg.getCity())){
            dist.setCity(arg.getCity());
        }
        if(StringUtils.isNotBlank(arg.getWechat())){
            dist.setWechat(arg.getWechat());
        }
        if(StringUtils.isNotBlank(arg.getQq())){
            dist.setQq(arg.getQq());
        }
        if(StringUtils.isNotBlank(arg.getEmail())){
            dist.setEmail(arg.getEmail());
        }
        if(StringUtils.isNotBlank(arg.getRoles())){
            dist.setRoles(arg.getRoles());
        }
        return dist;
    }

}
