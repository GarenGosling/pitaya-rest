package org.garen.pitaya.service;

import org.garen.pitaya.mybatis.domain.SysRole;
import org.garen.pitaya.mybatis.domain.SysRoleQuery;
import org.garen.pitaya.mybatis.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleManage extends BaseManage<Long>{
    @Autowired
    SysRoleService<SysRole, SysRoleQuery, Long> service;

    @Override
    public SysRoleService<SysRole, SysRoleQuery, Long> getService() {
        return service;
    }


}
