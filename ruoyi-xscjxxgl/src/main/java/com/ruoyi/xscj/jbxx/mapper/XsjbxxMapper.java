// Todo PJX
// Todo WJS
package com.ruoyi.xscj.jbxx.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import com.ruoyi.xscj.jbxx.domain.Xsjbxx;

/**
 * 学生基本信息Mapper接口（注解方式）
 *
 * @author LukAsy_
 * @date 2025-04-19
 */
@Mapper
public interface XsjbxxMapper {

    /**
     * 查询学生基本信息
     *
     * @param xsjbxxId 学生基本信息主键
     * @return 学生基本信息
     */
    @Select("SELECT xsjbxx_id, xm, xh, xb, bj, yx, xj_zt, create_by, create_time, update_by, update_time, user_id, dept_id, ss_user_id " +
            "FROM xscj_xsjbxx WHERE xsjbxx_id = #{xsjbxxId}")
    @Results(id = "XsjbxxResult", value = {
            @Result(property = "xsjbxxId", column = "xsjbxx_id"),
            @Result(property = "xm", column = "xm"),
            @Result(property = "xh", column = "xh"),
            @Result(property = "xb", column = "xb"),
            @Result(property = "bj", column = "bj"),
            @Result(property = "yx", column = "yx"),
            @Result(property = "xjZt", column = "xj_zt"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "deptId", column = "dept_id"),
            @Result(property = "ssUserId", column = "ss_user_id")
    })
    Xsjbxx selectXsjbxxByXsjbxxId(@Param("xsjbxxId") String xsjbxxId);

    /**
     * 查询学生基本信息列表
     *
     * @param xsjbxx 学生基本信息实体
     * @return 学生基本信息集合
     */
    @Select({
            "<script>",
            "SELECT xsjbxx_id, xm, xh, xb, bj, yx, xj_zt, create_by, create_time, update_by, update_time, user_id, dept_id, ss_user_id",
            "FROM xscj_xsjbxx",
            "<where>",
            "<if test=\"xm != null and xm != ''\">AND xm LIKE CONCAT('%', #{xm}, '%')</if>",
            "<if test=\"xh != null and xh != ''\">AND xh LIKE CONCAT('%', #{xh}, '%')</if>",
            "<if test=\"xb != null and xb != ''\">AND xb = #{xb}</if>",
            "<if test=\"bj != null and bj != ''\">AND bj LIKE CONCAT('%', #{bj}, '%')</if>",
            "<if test=\"yx != null and yx != ''\">AND yx LIKE CONCAT('%', #{yx}, '%')</if>",
            "<if test=\"xjZt != null and xjZt != ''\">AND xj_zt = #{xjZt}</if>",
            "<if test=\"userId != null\">AND user_id = #{userId}</if>",
            "<if test=\"deptId != null\">AND dept_id = #{deptId}</if>",
            "<if test=\"ssUserId != null\">AND ss_user_id = #{ssUserId}</if>",
            "</where>",
            "</script>"
    })
    @ResultMap("XsjbxxResult")
    List<Xsjbxx> selectXsjbxxList(Xsjbxx xsjbxx);



    /**
     * 删除学生基本信息
     *
     * @param xsjbxxId 学生基本信息主键
     * @return 删除条数
     */
    @Delete("DELETE FROM xscj_xsjbxx WHERE xsjbxx_id = #{xsjbxxId}")
    int deleteXsjbxxByXsjbxxId(@Param("xsjbxxId") String xsjbxxId);



}
