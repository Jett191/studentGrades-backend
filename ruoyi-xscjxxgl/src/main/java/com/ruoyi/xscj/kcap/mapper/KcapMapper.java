// Todo XSJ
// Todo SJB
package com.ruoyi.xscj.kcap.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import com.ruoyi.xscj.kcap.domain.Kcap;

/**
 * 课程安排Mapper接口（注解方式）
 *
 */
@Mapper
public interface KcapMapper {

    /**
     * 不分页查询课程安排
     *
     * @return 课程安排集合
     */
    //TODO: 邵靖彬 课程冲突检测
    @Select({
            "SELECT kcap_id AS kcapId, kc_name AS kcName, kc_qtime AS kcQtime, kc_ztime AS kcZtime,",
            "       kc_dd AS kcDd, sk_js AS skJs, create_by AS createBy, create_time AS createTime,",
            "       update_by AS updateBy, update_time AS updateTime, user_id AS userId, dept_id AS deptId",
            "FROM xscj_kcap"
    })
    @ResultMap("KcapResult")
    List<Kcap> selectKcApList();

    /**
     * 修改课程安排
     *
     * @param kcap 课程安排实体
     * @return 更新条数
     */
    //TODO: 邵靖彬 课程冲突检测
    @Update({
            "<script>",
            "UPDATE xscj_kcap",
            "<set>",
            "  <if test=\"kcName != null and kcName != ''\">kc_name = #{kcName},</if>",
            "  <if test=\"kcQtime != null\">kc_qtime = #{kcQtime},</if>",
            "  <if test=\"kcZtime != null\">kc_ztime = #{kcZtime},</if>",
            "  <if test=\"kcDd != null and kcDd != ''\">kc_dd = #{kcDd},</if>",
            "  <if test=\"skJs != null and skJs != ''\">sk_js = #{skJs},</if>",
            "  <if test=\"createBy != null\">create_by = #{createBy},</if>",
            "  <if test=\"createTime != null\">create_time = #{createTime},</if>",
            "  <if test=\"updateBy != null\">update_by = #{updateBy},</if>",
            "  <if test=\"updateTime != null\">update_time = #{updateTime},</if>",
            "  <if test=\"userId != null\">user_id = #{userId},</if>",
            "  <if test=\"deptId != null\">dept_id = #{deptId},</if>",
            "</set>",
            "WHERE kcap_id = #{kcapId}",
            "</script>"
    })
    int updateKcap(Kcap kcap);

    /**
     * 删除课程安排
     *
     * @param kcapId 课程安排主键
     * @return 删除条数
     */
    //TODO: 邵靖彬 课程冲突检测
    @Delete("DELETE FROM xscj_kcap WHERE kcap_id = #{kcapId}")
    int deleteKcapByKcapId(@Param("kcapId") String kcapId);

    /**
     * 批量删除课程安排
     *
     * @param kcapIds 课程安排主键数组
     * @return 删除条数
     */
    //TODO：邵靖彬 课程冲突检测
    @Delete({
            "<script>",
            "DELETE FROM xscj_kcap WHERE kcap_id IN",
            "<foreach item=\"id\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">",
            "  #{id}",
            "</foreach>",
            "</script>"
    })
    int deleteKcapByKcapIds(@Param("array") String[] kcapIds);
}
