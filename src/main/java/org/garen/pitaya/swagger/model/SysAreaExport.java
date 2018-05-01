package org.garen.pitaya.swagger.model;

import lombok.Data;

@Data
public class SysAreaExport {
    private String code;
    private String name;
    private String parentCode;
    private String parentName;
    private String fullName;
    private String fullPath;
    private String type;
}
