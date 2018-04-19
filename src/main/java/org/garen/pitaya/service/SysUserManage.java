package org.garen.pitaya.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.garen.pitaya.mybatis.domain.SysUser;
import org.garen.pitaya.mybatis.domain.SysUserQuery;
import org.garen.pitaya.mybatis.service.SysUserService;
import org.garen.pitaya.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysUserManage extends BaseManage<Long>{
    @Autowired
    SysUserService<SysUser, SysUserQuery, Long> service;

    @Override
    public SysUserService<SysUser, SysUserQuery, Long> getService() {
        return service;
    }

    /**
     * 分页构建query
     * @param code
     * @param nickName
     * @param realName
     * @param phone
     * @return
     */
    public SysUserQuery buildQuery(String code,String nickName,String realName,String phone){
        SysUserQuery query = new SysUserQuery();
        SysUserQuery.Criteria criteria = query.createCriteria();
            if(StringUtils.isNotBlank(code)){
                criteria.andCodeEqualTo(code.trim());
            }
            if(StringUtils.isNotBlank(nickName)){
                criteria.andNickNameEqualTo(nickName.trim());
            }
            if(StringUtils.isNotBlank(realName)){
                criteria.andRealNameLike("%"+realName.trim()+"%");
            }
            if(StringUtils.isNotBlank(phone)){
                criteria.andPhoneEqualTo(phone.trim());
            }
        return query;
    }

    /**
     * 分页列表
     * @param start
     * @param length
     * @param code
     * @param nickName
     * @param realName
     * @param phone
     * @return
     */
    public List<SysUser> getByPage(Integer start,Integer length,String code,String nickName,String realName,String phone){
        start = (start == null?0:start);
        length = (length == null?10:length);
        SysUserQuery query = buildQuery(code, nickName, realName, phone);
        query.setOrderByClause("id desc");
        return getService().findBy(new RowBounds(start, length), query);
    }

    /**
     * 分页总数量
     * @param code
     * @param nickName
     * @param realName
     * @param phone
     * @return
     */
    public int getPageCount(String code,String nickName,String realName,String phone) {
        return getService().countByExample(buildQuery(code, nickName, realName, phone));
    }

    /**
     * 昵称查询
     * @param nickName
     * @return
     */
    public SysUser getByNickName(String nickName){
        if(StringUtils.isBlank(nickName)){
            return null;
        }
        return getSingleByParamsOr(nickName, null, null, null, null, null);
    }

    /**
     * 手机号查询
     * @param phone
     * @return
     */
    public SysUser getByPhone(String phone){
        if(StringUtils.isBlank(phone)){
            return null;
        }
        return getSingleByParamsOr(null, phone, null, null, null, null);
    }

    /**
     * 身份证号查询
     * @param idNumber
     * @return
     */
    public SysUser getByIdNumber(String idNumber){
        if(StringUtils.isBlank(idNumber)){
            return null;
        }
        return getSingleByParamsOr(null, null, idNumber, null, null, null);
    }

    /**
     * 微信号查询
     * @param wechat
     * @return
     */
    public SysUser getByWechat(String wechat){
        if(StringUtils.isBlank(wechat)){
            return null;
        }
        return getSingleByParamsOr(null, null, null, wechat, null, null);
    }

    /**
     * qq号查询
     * @param qq
     * @return
     */
    public SysUser getByQq(String qq){
        if(StringUtils.isBlank(qq)){
            return null;
        }
        return getSingleByParamsOr(null, null, null, null, qq, null);
    }

    /**
     * 邮箱号查询
     * @param email
     * @return
     */
    public SysUser getByEmail(String email){
        if(StringUtils.isBlank(email)){
            return null;
        }
        return getSingleByParamsOr(null, null, null, null, email, null);
    }

    /**
     * 通过属性查询单个用户，组合查询方式“或”
     * @param nickName
     * @param phone
     * @param idNumber
     * @param wechat
     * @param qq
     * @param email
     * @return
     */
    public SysUser getSingleByParamsOr(String nickName, String phone, String idNumber, String wechat, String qq, String email){
        List<SysUser> listByParamsOr = getListByParamsOr(nickName, phone, idNumber, wechat, qq, email);
        if(!CollectionUtils.isEmpty(listByParamsOr) && listByParamsOr.size() > 0){
            return listByParamsOr.get(0);
        }
        return null;
    }

    /**
     * 通过属性查询多个用户，组合查询方式“或”
     * @param nickName
     * @param phone
     * @param idNumber
     * @param wechat
     * @param qq
     * @param email
     * @return
     */
    public List<SysUser> getListByParamsOr(String nickName, String phone, String idNumber, String wechat, String qq, String email){
        SysUserQuery query = new SysUserQuery();
        SysUserQuery.Criteria criteria = query.or();
        if(StringUtils.isNotBlank(nickName)){
            criteria.andNickNameEqualTo(nickName);
        }
        if(StringUtils.isNotBlank(phone)){
            criteria.andPhoneEqualTo(phone);
        }
        if(StringUtils.isNotBlank(idNumber)){
            criteria.andIdNumberEqualTo(idNumber);
        }
        if(StringUtils.isNotBlank(wechat)){
            criteria.andWechatEqualTo(wechat);
        }
        if(StringUtils.isNotBlank(qq)){
            criteria.andQqEqualTo(qq);
        }
        if(StringUtils.isNotBlank(email)){
            criteria.andEmailEqualTo(email);
        }
        return getService().findBy(query);
    }
    /**
     * 新增
     * @param sysUser
     * @return
     */
    public boolean save(org.garen.pitaya.swagger.model.SysUser sysUser){
        SysUser dist = buildSysUser(sysUser);
        int save = getService().save(dist);
        if(save == 1){
            return true;
        }
        return false;
    }

    private SysUser buildSysUser(org.garen.pitaya.swagger.model.SysUser sysUser){
        if(sysUser == null){
            return null;
        }
        SysUser dist = new SysUser();
        dist.setCode(buildCode());
        dist.setNickName(buildNickName(sysUser.getNickName(), dist.getCode()));
        dist.setRealName(sysUser.getRealName());
        dist.setPassword(buildPassword(sysUser.getPassword()));
        dist.setPhone(sysUser.getPhone());
        dist.setIdNumber(sysUser.getIdNumber());
        dist.setProvince(sysUser.getProvince());
        dist.setCity(sysUser.getCity());
        dist.setWechat(sysUser.getWechat());
        dist.setQq(sysUser.getQq());
        dist.setEmail(sysUser.getEmail());
        dist.setRoles(sysUser.getRoles());
        dist.setCreateTime(new Date());
        return dist;
    }

    private String buildCode(){
        return UUID.randomUUID().toString();
    }
    private String buildNickName(String nickName, String code){
        if(StringUtils.isBlank(nickName)){
            return code;
        }
        return nickName;
    }
    private String buildPassword(String password){
        return MD5Util.getMD5String(password);
    }
}
