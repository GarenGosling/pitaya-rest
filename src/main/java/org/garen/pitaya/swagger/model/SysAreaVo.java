package org.garen.pitaya.swagger.model;

public class SysAreaVo {
    private Long id;
    private Integer parentId;
    private String name;
    private String fullname;
    private String fullpath;
    private String type;

    public SysAreaVo() {
    }

    public SysAreaVo(Long id, Integer parentId, String name, String fullname, String fullpath, String type) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.fullname = fullname;
        this.fullpath = fullpath;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
