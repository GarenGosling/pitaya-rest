package org.garen.pitaya.swagger.model;

import lombok.Data;

import java.util.Date;

@Data
public class TPostVo {
    private String code;
    private String name;
    private String orgCode;
    private String fullName;
    private String fullPath;
    private Boolean available;
    private Integer level;
}
