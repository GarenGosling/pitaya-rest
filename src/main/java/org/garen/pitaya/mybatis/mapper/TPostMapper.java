package org.garen.pitaya.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.garen.pitaya.mybatis.domain.TPost;
import org.garen.pitaya.mybatis.domain.TPostQuery;

import java.io.Serializable;
import java.util.List;

public interface TPostMapper <T,Q,PK extends Serializable> extends CommonMapper<T, Q, PK>{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int countByExample(TPostQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int deleteByExample(TPostQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int insert(TPost record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int insertSelective(TPost record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    List<TPost> selectByExample(TPostQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    TPost selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int updateByExampleSelective(@Param("record") TPost record, @Param("example") TPostQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int updateByExample(@Param("record") TPost record, @Param("example") TPostQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int updateByPrimaryKeySelective(TPost record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_post
     *
     * @mbggenerated Fri Jun 01 10:07:40 CST 2018
     */
    int updateByPrimaryKey(TPost record);
}