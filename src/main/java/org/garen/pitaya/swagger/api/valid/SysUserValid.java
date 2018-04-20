package org.garen.pitaya.swagger.api.valid;

import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.swagger.model.SysUser;
import org.garen.pitaya.util.IdNumValidUtil;
import org.garen.pitaya.util.PhoneValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SysUserValid {

    @Autowired
    SysUserManage sysUserManage;

    public void validSave(SysUser sysUser){
        if(sysUser == null){
            throw new BadRequestException("新增用户不能为空");
        }
        // 昵称
        if(StringUtils.isNotBlank(sysUser.getNickName())){
            org.garen.pitaya.mybatis.domain.SysUser byNickName = sysUserManage.getByNickName(sysUser.getNickName());
            if(byNickName != null){
                throw new BadRequestException("昵称已存在");
            }
        }
        // 姓名
        if(StringUtils.isBlank(sysUser.getRealName())){
            throw new BadRequestException("姓名不能为空");
        }
        // 手机号
        if(StringUtils.isBlank(sysUser.getPhone())){
            throw new BadRequestException("手机号不能为空");
        }
        if(!PhoneValidUtil.isPhone(sysUser.getPhone())){
            throw new BadRequestException("手机号有误");
        }
        org.garen.pitaya.mybatis.domain.SysUser byPhone = sysUserManage.getByPhone(sysUser.getPhone());
        if(byPhone != null){
            throw new BadRequestException("手机号已存在");
        }
        // 身份证
        if(StringUtils.isNotBlank(sysUser.getIdNumber())){
            if(!IdNumValidUtil.validID(sysUser.getIdNumber(), false, false)){
                throw new BadRequestException("身份证号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byIdNumber = sysUserManage.getByIdNumber(sysUser.getIdNumber());
            if(byIdNumber != null){
                throw new BadRequestException("身份证号已存在");
            }
        }
        // 微信
        if(StringUtils.isNotBlank(sysUser.getWechat())){
            org.garen.pitaya.mybatis.domain.SysUser byWechat = sysUserManage.getByWechat(sysUser.getWechat());
            if(byWechat != null){
                throw new BadRequestException("微信号已存在");
            }
        }
        // QQ
        if(StringUtils.isNotBlank(sysUser.getQq())){
            org.garen.pitaya.mybatis.domain.SysUser byQq = sysUserManage.getByQq(sysUser.getQq());
            if(byQq != null){
                throw new BadRequestException("QQ号已存在");
            }
        }
        // email
        if(StringUtils.isNotBlank(sysUser.getEmail())){
            if(sysUser.getEmail().indexOf('@')<1 || sysUser.getEmail().split("@").length>2){
                throw new BadRequestException("邮箱号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byEmail = sysUserManage.getByEmail(sysUser.getEmail());
            if(byEmail != null){
                throw new BadRequestException("邮箱号已存在");
            }
        }
    }

    public void validModify(SysUser sysUser){
        if(sysUser == null){
            throw new BadRequestException("编辑用户不能为空");
        }
        // ID
        if(StringUtils.isBlank(sysUser.getCode())){
            throw new BadRequestException("ID不能为空");
        }
        org.garen.pitaya.mybatis.domain.SysUser byCode = sysUserManage.getByCode(sysUser.getCode());
        // 昵称
        if(StringUtils.isNotBlank(sysUser.getNickName())){
            if(!byCode.getNickName().equals(sysUser.getNickName())){
                org.garen.pitaya.mybatis.domain.SysUser byNickName = sysUserManage.getByNickName(sysUser.getNickName());
                if(byNickName != null){
                    throw new BadRequestException("昵称已存在");
                }
            }
        }
        // 手机号
        if(StringUtils.isNotBlank(sysUser.getPhone()) && !byCode.getPhone().equals(sysUser.getPhone())){
            if(!PhoneValidUtil.isPhone(sysUser.getPhone())){
                throw new BadRequestException("手机号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byPhone = sysUserManage.getByPhone(sysUser.getPhone());
            if(byPhone != null){
                throw new BadRequestException("手机号已存在");
            }
        }
        // 身份证号
        if(StringUtils.isNotBlank(sysUser.getIdNumber())){
            if(StringUtils.isBlank(byCode.getIdNumber()) || (StringUtils.isNotBlank(byCode.getIdNumber()) && !byCode.getIdNumber().equals(sysUser.getIdNumber()))){
                if(!IdNumValidUtil.validID(sysUser.getIdNumber(), false, false)){
                    throw new BadRequestException("身份证号有误");
                }
                org.garen.pitaya.mybatis.domain.SysUser byIdNumber = sysUserManage.getByIdNumber(sysUser.getIdNumber());
                if(byIdNumber != null){
                    throw new BadRequestException("身份证号已存在");
                }
            }
        }

        // TODO VALID
        // 微信
        if(StringUtils.isNotBlank(sysUser.getWechat())){
            org.garen.pitaya.mybatis.domain.SysUser byWechat = sysUserManage.getByWechat(sysUser.getWechat());
            if(byWechat != null){
                throw new BadRequestException("微信号已存在");
            }
        }
        // QQ
        if(StringUtils.isNotBlank(sysUser.getQq())){
            org.garen.pitaya.mybatis.domain.SysUser byQq = sysUserManage.getByQq(sysUser.getQq());
            if(byQq != null){
                throw new BadRequestException("QQ号已存在");
            }
        }
        // email
        if(StringUtils.isNotBlank(sysUser.getEmail())){
            if(sysUser.getEmail().indexOf('@')<1 || sysUser.getEmail().split("@").length>2){
                throw new BadRequestException("邮箱号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byEmail = sysUserManage.getByEmail(sysUser.getEmail());
            if(byEmail != null){
                throw new BadRequestException("邮箱号已存在");
            }
        }
    }
}
