package org.garen.pitaya.mybatis.domain;

import java.io.Serializable;

public class SysPermission implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.id
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.name
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.type
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.url
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.parent_code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private Long parentCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.full_code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private String fullCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.available
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private Boolean available;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_permission
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.id
     *
     * @return the value of sys_permission.id
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.id
     *
     * @param id the value for sys_permission.id
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.code
     *
     * @return the value of sys_permission.code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.code
     *
     * @param code the value for sys_permission.code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.name
     *
     * @return the value of sys_permission.name
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.name
     *
     * @param name the value for sys_permission.name
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.type
     *
     * @return the value of sys_permission.type
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.type
     *
     * @param type the value for sys_permission.type
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.url
     *
     * @return the value of sys_permission.url
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.url
     *
     * @param url the value for sys_permission.url
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.parent_code
     *
     * @return the value of sys_permission.parent_code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public Long getParentCode() {
        return parentCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.parent_code
     *
     * @param parentCode the value for sys_permission.parent_code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.full_code
     *
     * @return the value of sys_permission.full_code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public String getFullCode() {
        return fullCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.full_code
     *
     * @param fullCode the value for sys_permission.full_code
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public void setFullCode(String fullCode) {
        this.fullCode = fullCode == null ? null : fullCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.available
     *
     * @return the value of sys_permission.available
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.available
     *
     * @param available the value for sys_permission.available
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysPermission other = (SysPermission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getParentCode() == null ? other.getParentCode() == null : this.getParentCode().equals(other.getParentCode()))
            && (this.getFullCode() == null ? other.getFullCode() == null : this.getFullCode().equals(other.getFullCode()))
            && (this.getAvailable() == null ? other.getAvailable() == null : this.getAvailable().equals(other.getAvailable()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getParentCode() == null) ? 0 : getParentCode().hashCode());
        result = prime * result + ((getFullCode() == null) ? 0 : getFullCode().hashCode());
        result = prime * result + ((getAvailable() == null) ? 0 : getAvailable().hashCode());
        return result;
    }
}