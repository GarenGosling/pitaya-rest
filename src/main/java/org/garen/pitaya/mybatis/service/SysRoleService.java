package org.garen.pitaya.mybatis.service;

import org.garen.pitaya.mybatis.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SysRoleService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    SysRoleMapper<T, Q, PK> sysRoleMapper;
    @Override
    public SysRoleMapper<T, Q, PK> getMapper() {
        return sysRoleMapper;
    }
}
