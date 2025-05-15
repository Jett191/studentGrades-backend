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
     * 新增学生基本信息
     *
     * @param xsjbxx 学生基本信息实体
     * @return 插入条数
     */
    @Insert({
            "<script>",
            "INSERT INTO xscj_xsjbxx",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"xsjbxxId != null\">xsjbxx_id,</if>",
            "<if test=\"xm != null and xm != ''\">xm,</if>",
            "<if test=\"xh != null and xh != ''\">xh,</if>",
            "<if test=\"xb != null and xb != ''\">xb,</if>",
            "<if test=\"bj != null and bj != ''\">bj,</if>",
            "<if test=\"yx != null and yx != ''\">yx,</if>",
            "<if test=\"xjZt != null and xjZt != ''\">xj_zt,</if>",
            "<if test=\"createBy != null\">create_by,</if>",
            "<if test=\"createTime != null\">create_time,</if>",
            "<if test=\"updateBy != null\">update_by,</if>",
            "<if test=\"updateTime != null\">update_time,</if>",
            "<if test=\"userId != null\">user_id,</if>",
            "<if test=\"deptId != null\">dept_id,</if>",
            "<if test=\"ssUserId != null\">ss_user_id,</if>",
            "</trim>",
            "VALUES",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"xsjbxxId != null\">#{xsjbxxId},</if>",
            "<if test=\"xm != null and xm != ''\">#{xm},</if>",
            "<if test=\"xh != null and xh != ''\">#{xh},</if>",
            "<if test=\"xb != null and xb != ''\">#{xb},</if>",
            "<if test=\"bj != null and bj != ''\">#{bj},</if>",
            "<if test=\"yx != null and yx != ''\">#{yx},</if>",
            "<if test=\"xjZt != null and xjZt != ''\">#{xjZt},</if>",
            "<if test=\"createBy != null\">#{createBy},</if>",
            "<if test=\"createTime != null\">#{createTime},</if>",
            "<if test=\"updateBy != null\">#{updateBy},</if>",
            "<if test=\"updateTime != null\">#{updateTime},</if>",
            "<if test=\"userId != null\">#{userId},</if>",
            "<if test=\"deptId != null\">#{deptId},</if>",
            "<if test=\"ssUserId != null\">#{ssUserId},</if>",
            "</trim>",
            "</script>"
    })
    int insertXsjbxx(Xsjbxx xsjbxx);

    /**
     * 更新学生基本信息
     *
     * @param xsjbxx 学生基本信息实体
     * @return 更新条数
     */
    @Update({
            "<script>",
            "UPDATE xscj_xsjbxx",
            "<set>",
            "<if test=\"xm != null and xm != ''\">xm = #{xm},</if>",
            "<if test=\"xh != null and xh != ''\">xh = #{xh},</if>",
            "<if test=\"xb != null and xb != ''\">xb = #{xb},</if>",
            "<if test=\"bj != null and bj != ''\">bj = #{bj},</if>",
            "<if test=\"yx != null and yx != ''\">yx = #{yx},</if>",
            "<if test=\"xjZt != null and xjZt != ''\">xj_zt = #{xjZt},</if>",
            "<if test=\"createBy != null\">create_by = #{createBy},</if>",
            "<if test=\"createTime != null\">create_time = #{createTime},</if>",
            "<if test=\"updateBy != null\">update_by = #{updateBy},</if>",
            "<if test=\"updateTime != null\">update_time = #{updateTime},</if>",
            "<if test=\"userId != null\">user_id = #{userId},</if>",
            "<if test=\"deptId != null\">dept_id = #{deptId},</if>",
            "<if test=\"ssUserId != null\">ss_user_id = #{ssUserId},</if>",
            "</set>",
            "WHERE xsjbxx_id = #{xsjbxxId}",
            "</script>"
    })
    int updateXsjbxx(Xsjbxx xsjbxx);

    /**
     * 删除学生基本信息
     *
     * @param xsjbxxId 学生基本信息主键
     * @return 删除条数
     */
    @Delete("DELETE FROM xscj_xsjbxx WHERE xsjbxx_id = #{xsjbxxId}")
    int deleteXsjbxxByXsjbxxId(@Param("xsjbxxId") String xsjbxxId);

    /**
     * 批量删除学生基本信息
     *
     * @param xsjbxxIds 学生基本信息主键数组
     * @return 删除条数
     */
    @Delete({
            "<script>",
            "DELETE FROM xscj_xsjbxx WHERE xsjbxx_id IN",
            "<foreach item=\"id\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteXsjbxxByXsjbxxIds(@Param("array") String[] xsjbxxIds);

}
