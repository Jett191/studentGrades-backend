// Todo SJB
package com.ruoyi.xscj.kcpj.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import com.ruoyi.xscj.kcpj.domain.Kcpj;

/**
 * 课程评价Mapper接口（注解方式）
 *
 */
@Mapper
public interface KcpjMapper {

    /**
     * 查询课程评价
     *
     * @param kcpjId 课程评价主键
     * @return 课程评价
     */
    @Select("SELECT kcpj_id, kc_name, kc_pf, kc_fk, create_by, create_time, update_by, update_time, user_id, dept_id "
            + "FROM xscj_kcpj WHERE kcpj_id = #{kcpjId}")
    @Results(id = "KcpjResult", value = {
            @Result(property = "kcpjId", column = "kcpj_id"),
            @Result(property = "kcName", column = "kc_name"),
            @Result(property = "kcPf",   column = "kc_pf"),
            @Result(property = "kcFk",   column = "kc_fk"),
            @Result(property = "createBy",   column = "create_by"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateBy",   column = "update_by"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "userId",     column = "user_id"),
            @Result(property = "deptId",     column = "dept_id")
    })
    Kcpj selectKcpjByKcpjId(@Param("kcpjId") String kcpjId);

    /**
     * 查询课程评价列表
     *
     * @param kcpj 课程评价实体，用于动态条件
     * @return 课程评价集合
     */
    @Select({
            "<script>",
            "SELECT kcpj_id, kc_name, kc_pf, kc_fk, create_by, create_time, update_by, update_time, user_id, dept_id",
            "FROM xscj_kcpj",
            "<where>",
            "  <if test=\"kcName != null and kcName != ''\">AND kc_name LIKE CONCAT('%', #{kcName}, '%')</if>",
            "  <if test=\"createBy != null and createBy != ''\">AND create_by LIKE CONCAT('%', #{createBy}, '%')</if>",
            "  <if test=\"userId != null\">AND user_id = #{userId}</if>",
            "  <if test=\"deptId != null\">AND dept_id = #{deptId}</if>",
            "</where>",
            "</script>"
    })
    @ResultMap("KcpjResult")
    List<Kcpj> selectKcpjList(Kcpj kcpj);

    /**
     * 新增课程评价
     *
     * @param kcpj 课程评价实体
     * @return 插入条数
     */
    @Insert({
            "<script>",
            "INSERT INTO xscj_kcpj",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "  <if test=\"kcpjId != null\">kcpj_id,</if>",
            "  <if test=\"kcName != null and kcName != ''\">kc_name,</if>",
            "  <if test=\"kcPf != null\">kc_pf,</if>",
            "  <if test=\"kcFk != null and kcFk != ''\">kc_fk,</if>",
            "  <if test=\"createBy != null\">create_by,</if>",
            "  <if test=\"createTime != null\">create_time,</if>",
            "  <if test=\"updateBy != null\">update_by,</if>",
            "  <if test=\"updateTime != null\">update_time,</if>",
            "  <if test=\"userId != null\">user_id,</if>",
            "  <if test=\"deptId != null\">dept_id,</if>",
            "</trim>",
            "VALUES",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "  <if test=\"kcpjId != null\">#{kcpjId},</if>",
            "  <if test=\"kcName != null and kcName != ''\">#{kcName},</if>",
            "  <if test=\"kcPf != null\">#{kcPf},</if>",
            "  <if test=\"kcFk != null and kcFk != ''\">#{kcFk},</if>",
            "  <if test=\"createBy != null\">#{createBy},</if>",
            "  <if test=\"createTime != null\">#{createTime},</if>",
            "  <if test=\"updateBy != null\">#{updateBy},</if>",
            "  <if test=\"updateTime != null\">#{updateTime},</if>",
            "  <if test=\"userId != null\">#{userId},</if>",
            "  <if test=\"deptId != null\">#{deptId},</if>",
            "</trim>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "kcpjId")
    int insertKcpj(Kcpj kcpj);

    /**
     * 修改课程评价
     *
     * @param kcpj 课程评价实体
     * @return 更新条数
     */
    @Update({
            "<script>",
            "UPDATE xscj_kcpj",
            "<set>",
            "  <if test=\"kcName != null and kcName != ''\">kc_name = #{kcName},</if>",
            "  <if test=\"kcPf != null\">kc_pf = #{kcPf},</if>",
            "  <if test=\"kcFk != null and kcFk != ''\">kc_fk = #{kcFk},</if>",
            "  <if test=\"createBy != null\">create_by = #{createBy},</if>",
            "  <if test=\"createTime != null\">create_time = #{createTime},</if>",
            "  <if test=\"updateBy != null\">update_by = #{updateBy},</if>",
            "  <if test=\"updateTime != null\">update_time = #{updateTime},</if>",
            "  <if test=\"userId != null\">user_id = #{userId},</if>",
            "  <if test=\"deptId != null\">dept_id = #{deptId},</if>",
            "</set>",
            "WHERE kcpj_id = #{kcpjId}",
            "</script>"
    })
    int updateKcpj(Kcpj kcpj);

    /**
     * 删除课程评价
     *
     * @param kcpjId 课程评价主键
     * @return 删除条数
     */
    @Delete("DELETE FROM xscj_kcpj WHERE kcpj_id = #{kcpjId}")
    int deleteKcpjByKcpjId(@Param("kcpjId") String kcpjId);

    /**
     * 批量删除课程评价
     *
     * @param kcpjIds 课程评价主键数组
     * @return 删除条数
     */
    @Delete({
            "<script>",
            "DELETE FROM xscj_kcpj WHERE kcpj_id IN",
            "<foreach item=\"id\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">",
            "  #{id}",
            "</foreach>",
            "</script>"
    })
    int deleteKcpjByKcpjIds(@Param("array") String[] kcpjIds);

}
