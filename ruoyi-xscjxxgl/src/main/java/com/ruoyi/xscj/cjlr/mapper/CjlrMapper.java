package com.ruoyi.xscj.cjlr.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import com.ruoyi.xscj.cjlr.domain.BjCj;
import com.ruoyi.xscj.cjlr.domain.CjTj;
import com.ruoyi.xscj.cjlr.domain.Cjlr;
import com.ruoyi.xscj.cjlr.domain.Gpa;

/**
 * 成绩录入Mapper接口（注解方式）
 */
@Mapper
public interface CjlrMapper {

    /**
     * 查询成绩录入
     *
     * @param cjlrId 成绩录入主键
     * @return 成绩录入
     */
    @Select("SELECT cjlr_id, xs_name, kc_name, kc_cj, create_by, create_time, update_by, update_time, user_id, dept_id " +
            "FROM xscj_cjlr WHERE cjlr_id = #{cjlrId}")
    @Results(id = "CjlrResult", value = {
            @Result(property = "cjlrId", column = "cjlr_id"),
            @Result(property = "xsName", column = "xs_name"),
            @Result(property = "kcName", column = "kc_name"),
            @Result(property = "kcCj", column = "kc_cj"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "deptId", column = "dept_id")
    })
    Cjlr selectCjlrByCjlrId(@Param("cjlrId") String cjlrId);

    /**
     * 查询成绩录入列表
     *
     * @param cjlr 成绩录入实体，用于动态条件
     * @return 成绩录入集合
     */
    @Select({
            "<script>",
            "SELECT cjlr_id, xs_name, kc_name, kc_cj, create_by, create_time, update_by, update_time, user_id, dept_id",
            "FROM xscj_cjlr",
            "<where>",
            "  <if test=\"xsName != null and xsName != ''\">AND xs_name LIKE CONCAT('%', #{xsName}, '%')</if>",
            "  <if test=\"kcName != null and kcName != ''\">AND kc_name LIKE CONCAT('%', #{kcName}, '%')</if>",
            "  <if test=\"userId != null\">AND user_id = #{userId}</if>",
            "  <if test=\"deptId != null\">AND dept_id = #{deptId}</if>",
            "</where>",
            "</script>"
    })
    @ResultMap("CjlrResult")
    List<Cjlr> selectCjlrList(Cjlr cjlr);
    

    /**
     * 学生成绩分析
     *
     * @param xsName 学生姓名
     * @return 学生成绩列表
     */
    @Select("SELECT xs_name AS xsName, kc_name AS kcName, kc_cj AS kcCj " +
            "FROM xscj_cjlr WHERE xs_name = #{xsName} ORDER BY kc_cj")
    @Results({
            @Result(property = "xsName", column = "xsName"),
            @Result(property = "kcName", column = "kcName"),
            @Result(property = "kcCj", column = "kcCj")
    })
    List<Cjlr> selectXscjfx(@Param("xsName") String xsName);

    /**
     * 查询GPA
     *
     * @return 学生GPA列表
     */
    @Select("SELECT xs_name AS xsName, AVG(kc_cj) AS gpa FROM xscj_cjlr GROUP BY xs_name")
    List<Gpa> selectGpaList();

    /**
     * 查询班级成绩排名
     *
     * @return 班级平均成绩排名列表
     */
    @Select("SELECT jbxx.bj AS bj, AVG(x.kc_cj) AS pjf " +
            "FROM xscj_cjlr x LEFT JOIN xscj_xsjbxx jbxx ON x.xs_name = jbxx.xm " +
            "GROUP BY jbxx.bj ORDER BY pjf DESC")
    List<BjCj> selectBjCjList();

    /**
     * 新增成绩录入
     *
     * @param cjlr 成绩录入实体
     * @return 插入条数
     */
    @Insert({
            "<script>",
            "INSERT INTO xscj_cjlr",
            "<trim prefix='(' suffix=')' suffixOverrides=','>",
            "  <if test=\"cjlrId != null\">cjlr_id,</if>",
            "  <if test=\"xsName != null and xsName != ''\">xs_name,</if>",
            "  <if test=\"kcName != null and kcName != ''\">kc_name,</if>",
            "  <if test=\"kcCj != null\">kc_cj,</if>",
            "  <if test=\"createBy != null\">create_by,</if>",
            "  <if test=\"createTime != null\">create_time,</if>",
            "  <if test=\"updateBy != null\">update_by,</if>",
            "  <if test=\"updateTime != null\">update_time,</if>",
            "  <if test=\"userId != null\">user_id,</if>",
            "  <if test=\"deptId != null\">dept_id,</if>",
            "</trim>",
            "VALUES",
            "<trim prefix='(' suffix=')' suffixOverrides=','>",
            "  <if test=\"cjlrId != null\">#{cjlrId},</if>",
            "  <if test=\"xsName != null and xsName != ''\">#{xsName},</if>",
            "  <if test=\"kcName != null and kcName != ''\">#{kcName},</if>",
            "  <if test=\"kcCj != null\">#{kcCj},</if>",
            "  <if test=\"createBy != null\">#{createBy},</if>",
            "  <if test=\"createTime != null\">#{createTime},</if>",
            "  <if test=\"updateBy != null\">#{updateBy},</if>",
            "  <if test=\"updateTime != null\">#{updateTime},</if>",
            "  <if test=\"userId != null\">#{userId},</if>",
            "  <if test=\"deptId != null\">#{deptId},</if>",
            "</trim>",
            "</script>"
    })
    int insertCjlr(Cjlr cjlr);

    /**
     * 更新成绩录入
     *
     * @param cjlr 成绩录入实体
     * @return 更新条数
     */
    @Update({
            "<script>",
            "UPDATE xscj_cjlr",
            "<set>",
            "  <if test=\"xsName != null and xsName != ''\">xs_name = #{xsName},</if>",
            "  <if test=\"kcName != null and kcName != ''\">kc_name = #{kcName},</if>",
            "  <if test=\"kcCj != null\">kc_cj = #{kcCj},</if>",
            "  <if test=\"createBy != null\">create_by = #{createBy},</if>",
            "  <if test=\"createTime != null\">create_time = #{createTime},</if>",
            "  <if test=\"updateBy != null\">update_by = #{updateBy},</if>",
            "  <if test=\"updateTime != null\">update_time = #{updateTime},</if>",
            "  <if test=\"userId != null\">user_id = #{userId},</if>",
            "  <if test=\"deptId != null\">dept_id = #{deptId},</if>",
            "</set>",
            "WHERE cjlr_id = #{cjlrId}",
            "</script>"
    })
    int updateCjlr(Cjlr cjlr);

    /**
     * 删除成绩录入
     *
     * @param cjlrId 成绩录入主键
     * @return 删除条数
     */
    @Delete("DELETE FROM xscj_cjlr WHERE cjlr_id = #{cjlrId}")
    int deleteCjlrByCjlrId(@Param("cjlrId") String cjlrId);

    /**
     * 批量删除成绩录入
     *
     * @param cjlrIds 成绩录入主键数组
     * @return 删除条数
     */
    @Delete({
            "<script>",
            "DELETE FROM xscj_cjlr WHERE cjlr_id IN",
            "<foreach item='id' collection='array' open='(' separator=',' close=')'>",
            "  #{id}",
            "</foreach>",
            "</script>"
    })
    int deleteCjlrByCjlrIds(@Param("array") String[] cjlrIds);
}
