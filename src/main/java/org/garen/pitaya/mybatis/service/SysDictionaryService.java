package org.garen.pitaya.mybatis.service;

import org.garen.pitaya.mybatis.mapper.SysDictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SysDictionaryService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    SysDictionaryMapper<T, Q, PK> sysDictionaryMapper;
    @Override
    public SysDictionaryMapper<T, Q, PK> getMapper() {
        return sysDictionaryMapper;
    }
}
