package org.garen.pitaya.mybatis.service;

import org.garen.pitaya.mybatis.mapper.THrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class THrService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    THrMapper<T, Q, PK> tHrMapper;
    @Override
    public THrMapper<T, Q, PK> getMapper() {
        return tHrMapper;
    }
}
