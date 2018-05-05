package org.garen.pitaya.mybatis.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysAreaDTO {
    private Long id;
    private String label;
    private Long parentId;
    private Boolean isLeaf;
    private List<SysAreaDTO> children;
}