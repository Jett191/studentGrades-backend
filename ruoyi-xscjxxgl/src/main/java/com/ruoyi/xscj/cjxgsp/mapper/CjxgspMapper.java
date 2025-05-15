// Todo SJB
package com.ruoyi.xscj.cjxgsp.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import com.ruoyi.xscj.cjxgsp.domain.Cjxgsp;

/**
 * 成绩修改审批Mapper接口（注解方式）
 *
 *  @author sjba1
 *  @date 2025/4/25
 */
@Mapper
public interface CjxgspMapper {

    /**
     * 查询成绩修改审批
     *
     * @param cjxgspId 成绩修改审批主键
     * @return 成绩修改审批
     */
    @Select("SELECT cjxgsp_id, xs_name, kc_name, xgq_cj, xgh_cj, sp_zt, create_by, create_time, update_by, update_time, user_id, cjlr_id " +
            "FROM xscj_cjxgsp WHERE cjxgsp_id = #{cjxgspId}")
    @Results(id = "CjxgspResult", value = {
            @Result(property = "cjxgspId", column = "cjxgsp_id"),
            @Result(property = "xsName", column = "xs_name"),
            @Result(property = "kcName", column = "kc_name"),
            @Result(property = "xgqCj", column = "xgq_cj"),
            @Result(property = "xghCj", column = "xgh_cj"),
            @Result(property = "spZt", column = "sp_zt"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "cjlrId", column = "cjlr_id")
    })
    Cjxgsp selectCjxgspByCjxgspId(@Param("cjxgspId") Integer cjxgspId);

    /**
     * 查询成绩修改审批列表
     *
     * @param cjxgsp 成绩修改审批实体，用于动态条件
     * @return 成绩修改审批集合
     */
    @Select({
            "<script>",
            "SELECT cjxgsp_id, xs_name, kc_name, xgq_cj, xgh_cj, sp_zt, create_by, create_time, update_by, update_time, user_id, cjlr_id",
            " FROM xscj_cjxgsp",
            " <where>",
            "   <if test=\"xsName != null and xsName != ''\">xs_name LIKE CONCAT('%', #{xsName}, '%')</if>",
            "   <if test=\"kcName != null and kcName != ''\">AND kc_name LIKE CONCAT('%', #{kcName}, '%')</if>",
            "   <if test=\"spZt != null and spZt != ''\">AND sp_zt = #{spZt}</if>",
            "   <if test=\"userId != null\">AND user_id = #{userId}</if>",
            " </where>",
            "</script>"
    })
    @ResultMap("CjxgspResult")
    List<Cjxgsp> selectCjxgspList(Cjxgsp cjxgsp);

    /**
     * 新增成绩修改审批
     *
     * @param cjxgsp 成绩修改审批实体
     * @return 插入条数
     */
    @Insert({
            "<script>",
            "INSERT INTO xscj_cjxgsp",
            " <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "   <if test=\"xsName != null and xsName != ''\">xs_name,</if>",
            "   <if test=\"kcName != null and kcName != ''\">kc_name,</if>",
            "   <if test=\"xgqCj != null\">xgq_cj,</if>",
            "   <if test=\"xghCj != null\">xgh_cj,</if>",
            "   <if test=\"spZt != null and spZt != ''\">sp_zt,</if>",
            "   <if test=\"createBy != null\">create_by,</if>",
            "   <if test=\"createTime != null\">create_time,</if>",
            "   <if test=\"updateBy != null\">update_by,</if>",
            "   <if test=\"updateTime != null\">update_time,</if>",
            "   <if test=\"userId != null\">user_id,</if>",
            "   <if test=\"cjlrId != null\">cjlr_id,</if>",
            " </trim>",
            " VALUES",
            " <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "   <if test=\"xsName != null and xsName != ''\">#{xsName},</if>",
            "   <if test=\"kcName != null and kcName != ''\">#{kcName},</if>",
            "   <if test=\"xgqCj != null\">#{xgqCj},</if>",
            "   <if test=\"xghCj != null\">#{xghCj},</if>",
            "   <if test=\"spZt != null and spZt != ''\">#{spZt},</if>",
            "   <if test=\"createBy != null\">#{createBy},</if>",
            "   <if test=\"createTime != null\">#{createTime},</if>",
            "   <if test=\"updateBy != null\">#{updateBy},</if>",
            "   <if test=\"updateTime != null\">#{updateTime},</if>",
            "   <if test=\"userId != null\">#{userId},</if>",
            "   <if test=\"cjlrId != null\">#{cjlrId},</if>",
            " </trim>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "cjxgspId")
    int insertCjxgsp(Cjxgsp cjxgsp);

    /**
     * 更新成绩修改审批
     *
     * @param cjxgsp 成绩修改审批实体
     * @return 更新条数
     */
    @Update({
            "<script>",
            "UPDATE xscj_cjxgsp",
            " <set>",
            "   <if test=\"xsName != null and xsName != ''\">xs_name = #{xsName},</if>",
            "   <if test=\"kcName != null and kcName != ''\">kc_name = #{kcName},</if>",
            "   <if test=\"xgqCj != null\">xgq_cj = #{xgqCj},</if>",
            "   <if test=\"xghCj != null\">xgh_cj = #{xghCj},</if>",
            "   <if test=\"spZt != null and spZt != ''\">sp_zt = #{spZt},</if>",
            "   <if test=\"createBy != null\">create_by = #{createBy},</if>",
            "   <if test=\"createTime != null\">create_time = #{createTime},</if>",
            "   <if test=\"updateBy != null\">update_by = #{updateBy},</if>",
            "   <if test=\"updateTime != null\">update_time = #{updateTime},</if>",
            "   <if test=\"userId != null\">user_id = #{userId},</if>",
            "   <if test=\"cjlrId != null\">cjlr_id = #{cjlrId},</if>",
            " </set>",
            " WHERE cjxgsp_id = #{cjxgspId}",
            "</script>"
    })
    int updateCjxgsp(Cjxgsp cjxgsp);

    /**
     * 删除成绩修改审批
     *
     * @param cjxgspId 成绩修改审批主键
     * @return 删除条数
     */
    @Delete("DELETE FROM xscj_cjxgsp WHERE cjxgsp_id = #{cjxgspId}")
    int deleteCjxgspByCjxgspId(@Param("cjxgspId") Integer cjxgspId);

    /**
     * 批量删除成绩修改审批
     *
     * @param cjxgspIds 成绩修改审批主键数组
     * @return 删除条数
     */
    @Delete({
            "<script>",
            "DELETE FROM xscj_cjxgsp WHERE cjxgsp_id IN",
            "<foreach item=\"id\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteCjxgspByCjxgspIds(@Param("array") Integer[] cjxgspIds);
}
