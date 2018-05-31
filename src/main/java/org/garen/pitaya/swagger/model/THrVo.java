package org.garen.pitaya.swagger.model;

import lombok.Data;

import java.util.Date;

@Data
public class THrVo {
    private String code;
    private String name;
    private String orgCode;
    private String postCode;
    private String fullName;
    private String fullPath;
    private String idNum;
    private String phone;
    private String emergencyContact;
    private String emergencyContactPhone;
    private String email;
    private String maritalStatus;
    private String national;
    private String political;
    private String degree;
    private String nativePlace;
    private String domicilePlace;
    private Date workDate;
    private Date entryDate;
    private String rankName;
    private Boolean regular;
    private Date regularDate;
    private Double workAge;
    private String workStatus;
    private Boolean available;
    private Date createTime;
    private Date updateTime;
}
