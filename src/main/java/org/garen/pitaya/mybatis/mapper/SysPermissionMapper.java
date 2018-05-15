package org.garen.pitaya.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.garen.pitaya.mybatis.domain.SysPermission;
import org.garen.pitaya.mybatis.domain.SysPermissionQuery;

import java.io.Serializable;
import java.util.List;

public interface SysPermissionMapper <T,Q,PK extends Serializable> extends CommonMapper<T, Q,PK>{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int countByExample(SysPermissionQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int deleteByExample(SysPermissionQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int insert(SysPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int insertSelective(SysPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    List<SysPermission> selectByExample(SysPermissionQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    SysPermission selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int updateByExampleSelective(@Param("record") SysPermission record, @Param("example") SysPermissionQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int updateByExample(@Param("record") SysPermission record, @Param("example") SysPermissionQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int updateByPrimaryKeySelective(SysPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue May 15 16:47:21 CST 2018
     */
    int updateByPrimaryKey(SysPermission record);
}