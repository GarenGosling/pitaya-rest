package org.garen.pitaya.swagger.model;

import lombok.Data;

@Data
public class TOrgVo {
    private String code;
    private String name;
    private String parentCode;
    private String fullName;
    private String fullPath;
    private Boolean available;
    private Integer level;
}
