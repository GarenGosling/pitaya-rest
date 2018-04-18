package org.garen.pitaya.service;

import org.garen.pitaya.mybatis.domain.SysUser;
import org.garen.pitaya.mybatis.domain.SysUserQuery;
import org.garen.pitaya.mybatis.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionManage extends BaseManage<Long>{
    @Autowired
    SysPermissionService<SysUser, SysUserQuery, Long> service;

    @Override
    public SysPermissionService<SysUser, SysUserQuery, Long> getService() {
        return service;
    }


}
