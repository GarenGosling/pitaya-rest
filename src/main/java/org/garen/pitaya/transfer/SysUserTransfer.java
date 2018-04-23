package org.garen.pitaya.transfer;

import org.apache.commons.lang.StringUtils;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.swagger.model.SysUser;
import org.garen.pitaya.swagger.model.SysUserExport;
import org.garen.pitaya.util.MD5Util;
import org.garen.pitaya.util.TransferUtil;
import org.garen.pitaya.util.UniqueCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 转换辅助类
 *
 * @author Garen Gosling
 * @create 2018-04-21 12:20
 * @since v1.0
 */
@Component
public class SysUserTransfer {

    @Autowired
    SysUserManage sysUserManage;

    public SysUserExport exportExcelETE(Map<String, Object> map){
        SysUserExport dist = new SysUserExport();
        dist.setNickName((String) map.get("nick_name"));
        dist.setRealName((String) map.get("real_name"));
        dist.setPhone((String) map.get("phone"));
        dist.setIdNumber((String) map.get("id_number"));
        dist.setProvince((String) map.get("province"));
        dist.setCity((String) map.get("city"));
        dist.setWechat((String) map.get("wechat"));
        dist.setQq((String) map.get("qq"));
        dist.setEmail((String) map.get("email"));
        dist.setRoles((String) map.get("roles"));
        return dist;
    }

}
