package org.garen.pitaya.swagger.model;

/**
 * 查询参数对象
 *
 * @author Garen Gosling
 * @create 2018-04-21 14:11
 * @since v1.0
 */
public class SysAreaSearch {
    private Integer start;
    private Integer length;
    private Integer parentId;
    private String name;
    private Integer isParent;
    private String type;

    public SysAreaSearch() {
    }

    public SysAreaSearch(Integer start, Integer length, Integer parentId, String name, Integer isParent, String type) {
        this.start = start;
        this.length = length;
        this.parentId = parentId;
        this.name = name;
        this.isParent = isParent;
        this.type = type;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
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

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
