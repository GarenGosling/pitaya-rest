package org.garen.pitaya.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.garen.pitaya.mybatis.domain.SysUser;
import org.garen.pitaya.mybatis.domain.SysUserQuery;
import org.garen.pitaya.mybatis.service.SysUserService;
import org.garen.pitaya.swagger.model.SysUserSearch;
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
     * @param sysUserSearch
     * @return
     */
    public SysUserQuery buildQuery(SysUserSearch sysUserSearch){
        SysUserQuery query = new SysUserQuery();
        SysUserQuery.Criteria criteria = query.createCriteria();
            if(StringUtils.isNotBlank(sysUserSearch.getCode())){
                criteria.andCodeEqualTo(sysUserSearch.getCode().trim());
            }
            if(StringUtils.isNotBlank(sysUserSearch.getNickName())){
                criteria.andNickNameEqualTo(sysUserSearch.getNickName().trim());
            }
            if(StringUtils.isNotBlank(sysUserSearch.getRealName())){
                criteria.andRealNameLike("%"+sysUserSearch.getRealName().trim()+"%");
            }
            if(StringUtils.isNotBlank(sysUserSearch.getPhone())){
                criteria.andPhoneEqualTo(sysUserSearch.getPhone().trim());
            }
        return query;
    }

    /**
     * 分页列表
     * @param sysUserSearch
     * @return
     */
    public List<SysUser> getByPage(SysUserSearch sysUserSearch){
        if(sysUserSearch.getStart() == null){
            sysUserSearch.setStart(0);
        }
        if(sysUserSearch.getLength() == null){
            sysUserSearch.setLength(10);
        }
        SysUserQuery query = buildQuery(sysUserSearch);
        query.setOrderByClause("id desc");
        return getService().findBy(new RowBounds(sysUserSearch.getStart(), sysUserSearch.getLength()), query);
    }

    /**
     * 分页总数量
     * @param sysUserSearch
     * @return
     */
    public int getPageCount(SysUserSearch sysUserSearch) {
        return getService().countByExample(buildQuery(sysUserSearch));
    }

    /**
     * 编码查询
     * @param code
     * @return
     */
    public SysUser getByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        return getSingleByParamsOr(code, null, null, null, null, null, null);
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
        return getSingleByParamsOr(null, nickName, null, null, null, null, null);
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
        return getSingleByParamsOr(null,null, phone, null, null, null, null);
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
        return getSingleByParamsOr(null,null, null, idNumber, null, null, null);
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
        return getSingleByParamsOr(null,null, null, null, wechat, null, null);
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
        return getSingleByParamsOr(null,null, null, null, null, qq, null);
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
        return getSingleByParamsOr(null,null, null, null, null, email, null);
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
    public SysUser getSingleByParamsOr(String code, String nickName, String phone, String idNumber, String wechat, String qq, String email){
        List<SysUser> listByParamsOr = getListByParamsOr(code, nickName, phone, idNumber, wechat, qq, email);
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
    public List<SysUser> getListByParamsOr(String code, String nickName, String phone, String idNumber, String wechat, String qq, String email){
        SysUserQuery query = new SysUserQuery();
        SysUserQuery.Criteria criteria = query.or();
        if(StringUtils.isNotBlank(code)){
            criteria.andCodeEqualTo(code);
        }
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



}
