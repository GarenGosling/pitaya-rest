package org.garen.pitaya.service.helper;

import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.util.MD5Util;
import org.garen.pitaya.util.RandomUtil;
import org.garen.pitaya.util.date.DateUtil;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 编码生成类
 *
 * @author Garen Gosling
 * @create 2018-04-21 12:52
 * @since v1.0
 */
@Component
public class SysUserHelper {
    /**
     * 用户编码
     * @return
     */
    public String createUserCode(){
        String t = Long.toHexString(Long.parseLong(DateUtil.now("yyyyMMddHHmmssSSS")));
        String r = Integer.toHexString(Integer.parseInt(RandomUtil.r9(10)));
        return "ID-" + t + r;
    }

    /**
     * 用户昵称
     * @param nickName
     * @return
     */
    public String createNickName(String nickName){
        if(StringUtils.isBlank(nickName)){
            String t = Long.toHexString(Long.parseLong(DateUtil.now("yyyyMMddHHmmssSSS").substring(2)));
            String r = Integer.toHexString(Integer.parseInt(RandomUtil.r9(1)));
            return "N-" + t + r;
        }
        return nickName;
    }

    /**
     * 密码加密
     * @param password
     * @return
     */
    public String encodePassword(String password){
        return MD5Util.getMD5String(password);
    }

//    public static void main(String[] args) {
//        for(int i=0;i<1000;i++){
//            System.out.println(new SysUserHelper().createUserCode());
//            System.out.println(new SysUserHelper().createNickName(null));
//        }
//    }
}
