package org.garen.pitaya.mybatis.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysPermissionDTO {
    private Long id;
    private Long parentId;
    private String label;
    private String type;
    private String url;
    private Boolean available;
    private List<SysPermissionDTO> children;
}