package org.garen.pitaya.mybatis.service;

import org.garen.pitaya.mybatis.mapper.TOrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class THrService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    TOrgMapper<T, Q, PK> TOrg;
    @Override
    public TOrgMapper<T, Q, PK> getMapper() {
        return TOrg;
    }
}
