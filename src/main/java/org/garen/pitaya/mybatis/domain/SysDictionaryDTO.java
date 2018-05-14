package org.garen.pitaya.mybatis.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysDictionaryDTO {
    private Long id;
    private Long parentId;
    private String code;
    private String label;
    private String description;
    private String value;
    private String extend1;
    private String extend2;
    private String extend3;
    private List<SysDictionaryDTO> children;
}