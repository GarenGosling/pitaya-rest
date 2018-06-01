package org.garen.pitaya.mybatis.service;

import org.garen.pitaya.mybatis.mapper.TPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TPostService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    TPostMapper<T, Q, PK> tPostMapper;
    @Override
    public TPostMapper<T, Q, PK> getMapper() {
        return tPostMapper;
    }
}
