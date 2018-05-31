package org.garen.pitaya.mybatis.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TPostDTO{
    private String code;
    private String name;
    private String orgCode;
    private String fullName;
    private String fullPath;
    private Boolean available;
    private Integer level;
    private Date createTime;
    private Date updateTime;
}