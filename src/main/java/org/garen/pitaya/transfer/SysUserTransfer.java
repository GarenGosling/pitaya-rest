package org.garen.pitaya.transfer;

import org.apache.commons.lang.StringUtils;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.swagger.model.SysUser;
import org.garen.pitaya.swagger.model.SysUserExport;
import org.garen.pitaya.util.MD5Util;
import org.garen.pitaya.util.UniqueCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

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

    /**
     * 新增接口     Model -> DAO
     * @param sysUser
     * @return
     */
    public org.garen.pitaya.mybatis.domain.SysUser saveMTD(SysUser sysUser){
        org.garen.pitaya.mybatis.domain.SysUser dist = new org.garen.pitaya.mybatis.domain.SysUser();
        dist.setCode("ID-" + UniqueCodeUtil.idCode());
        if(StringUtils.isBlank(sysUser.getNickName())){
            dist.setNickName("N-" + UniqueCodeUtil.idCodeShort());
        }else{
            dist.setNickName(sysUser.getNickName());
        }
        dist.setRealName(sysUser.getRealName());
        dist.setPassword(MD5Util.getMD5String("111"));
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

    public org.garen.pitaya.mybatis.domain.SysUser importExcelETD(Map<Integer, String> map){
        org.garen.pitaya.mybatis.domain.SysUser dist = new org.garen.pitaya.mybatis.domain.SysUser();
        dist.setCode("ID-" + UniqueCodeUtil.idCode());
        if(StringUtils.isBlank(map.get(0))){
            dist.setNickName("N-" + UniqueCodeUtil.idCodeShort());
        }else{
            dist.setNickName(map.get(0));
        }
        dist.setRealName(map.get(1));
        dist.setPassword(MD5Util.getMD5String("111"));
        dist.setPhone(map.get(2));
        dist.setIdNumber(map.get(3));
        dist.setProvince(map.get(4));
        dist.setCity(map.get(5));
        dist.setWechat(map.get(6));
        dist.setQq(map.get(7));
        dist.setEmail(map.get(8));
        dist.setRoles(map.get(9));
        dist.setCreateTime(new Date());
        return dist;
    }

    public SysUserExport exportExcelETE(Map<String, Object> map){
        SysUserExport dist = new SysUserExport();
        dist.setNickName((String) map.get("nick_name"));
        dist.setRealName((String) map.get("real_name"));
        dist.setPhone((String) map.get("phone"));
        dist.setIdNumber((String) map.get("id_number"));
        dist.setProvince((String) map.get("province"));
        dist.setCity((String) map.get("city"));
        dist.setWechat((String) map.get("wechat"));
        dist.setQq((String) map.get("qq"));
        dist.setEmail((String) map.get("email"));
        dist.setRoles((String) map.get("roles"));
        return dist;
    }

}
