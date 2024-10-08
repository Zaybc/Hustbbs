package com.Hustbbs.community.mapper;

import java.util.List;
import com.Hustbbs.community.model.Nav;
import com.Hustbbs.community.model.NavExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface NavMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    long countByExample(NavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    int deleteByExample(NavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    int insert(Nav record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    int insertSelective(Nav record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    List<Nav> selectByExampleWithRowbounds(NavExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    List<Nav> selectByExample(NavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    Nav selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Nav record, @Param("example") NavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Nav record, @Param("example") NavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Nav record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nav
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Nav record);
}