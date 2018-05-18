package org.garen.pitaya.swagger.model;

import lombok.Data;
import org.garen.pitaya.mybatis.domain.SysPermissionDTO;
import org.garen.pitaya.mybatis.domain.SysRole;
import org.garen.pitaya.mybatis.domain.SysUser;

import java.util.List;

@Data
public class LoginInfo {
    private SysUser userInfo;
    private List<SysRole> roleList;
    private SysPermissionDTO permissionTree;
    private String ticket;
}
