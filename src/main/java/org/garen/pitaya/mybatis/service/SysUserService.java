package org.garen.pitaya.mybatis.service;

import org.garen.pitaya.mybatis.mapper.CommonMapper;
import org.garen.pitaya.mybatis.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SysUserService <T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    SysUserMapper<T, Q, PK> sysUserMapper;
    @Override
    public SysUserMapper<T, Q, PK> getMapper() {
        return sysUserMapper;
    }
}
