package org.garen.pitaya.service;

import org.garen.pitaya.mybatis.domain.SysUser;
import org.garen.pitaya.mybatis.domain.SysUserQuery;
import org.garen.pitaya.mybatis.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserManage extends BaseManage<Long>{
    @Autowired
    SysUserService<SysUser, SysUserQuery, Long> service;

    @Override
    public SysUserService<SysUser, SysUserQuery, Long> getService() {
        return service;
    }


}
