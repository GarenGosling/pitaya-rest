package org.garen.pitaya.swagger.model;

public class SysAreaExport {
    private String name;
    private String fullname;
    private String type;

    public SysAreaExport() {
    }

    public SysAreaExport(String name, String fullname, String type) {
        this.name = name;
        this.fullname = fullname;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
