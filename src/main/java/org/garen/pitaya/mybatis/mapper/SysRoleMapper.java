package org.garen.pitaya.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.garen.pitaya.mybatis.domain.SysRole;
import org.garen.pitaya.mybatis.domain.SysRoleQuery;

import java.io.Serializable;
import java.util.List;

public interface SysRoleMapper <T,Q,PK extends Serializable> extends CommonMapper<T, Q,PK>{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int countByExample(SysRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int deleteByExample(SysRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int insert(SysRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int insertSelective(SysRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    List<SysRole> selectByExample(SysRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    SysRole selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int updateByExampleSelective(@Param("record") SysRole record, @Param("example") SysRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int updateByExample(@Param("record") SysRole record, @Param("example") SysRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int updateByPrimaryKeySelective(SysRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated Wed Apr 18 17:07:26 CST 2018
     */
    int updateByPrimaryKey(SysRole record);
}