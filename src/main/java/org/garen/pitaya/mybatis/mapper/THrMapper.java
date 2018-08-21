package org.garen.pitaya.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.garen.pitaya.mybatis.domain.THr;
import org.garen.pitaya.mybatis.domain.THrQuery;

import java.io.Serializable;
import java.util.List;

public interface THrMapper <T,Q,PK extends Serializable> extends CommonMapper<T, Q, PK>{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int countByExample(THrQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int deleteByExample(THrQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int insert(THr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int insertSelective(THr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    List<THr> selectByExample(THrQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    THr selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int updateByExampleSelective(@Param("record") THr record, @Param("example") THrQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int updateByExample(@Param("record") THr record, @Param("example") THrQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int updateByPrimaryKeySelective(THr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hr
     *
     * @mbggenerated Fri Jun 01 14:39:16 CST 2018
     */
    int updateByPrimaryKey(THr record);
}