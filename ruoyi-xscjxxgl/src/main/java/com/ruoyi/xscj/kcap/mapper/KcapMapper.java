// Todo XSJ
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
     * 查询课程安排
     *
     * @param kcapId 课程安排主键
     * @return 课程安排
     */
    //TODO: 向世杰 课程安排管理
    @Select("SELECT kcap_id AS kcapId, kc_name AS kcName, kc_qtime AS kcQtime, kc_ztime AS kcZtime, " +
            "kc_dd AS kcDd, sk_js AS skJs, create_by AS createBy, create_time AS createTime, " +
            "update_by AS updateBy, update_time AS updateTime, user_id AS userId, dept_id AS deptId " +
            "FROM xscj_kcap WHERE kcap_id = #{kcapId}")
    Kcap selectKcapByKcapId(@Param("kcapId") String kcapId);

    /**
     * 查询课程安排列表
     *
     * @param kcap 课程安排实体，用于动态条件
     * @return 课程安排集合
     */
    //TODO: 向世杰 课程安排管理
    @Select({
            "<script>",
            "SELECT kcap_id AS kcapId, kc_name AS kcName, kc_qtime AS kcQtime, kc_ztime AS kcZtime,",
            "       kc_dd AS kcDd, sk_js AS skJs, create_by AS createBy, create_time AS createTime,",
            "       update_by AS updateBy, update_time AS updateTime, user_id AS userId, dept_id AS deptId",
            "FROM xscj_kcap",
            "<where>",
            "  <if test=\"kcName != null and kcName != ''\">AND kc_name LIKE CONCAT('%', #{kcName}, '%')</if>",
            "  <if test=\"kcDd != null and kcDd != ''\">AND kc_dd = #{kcDd}</if>",
            "  <if test=\"skJs != null and skJs != ''\">AND sk_js LIKE CONCAT('%', #{skJs}, '%')</if>",
            "  <if test=\"userId != null\">AND user_id = #{userId}</if>",
            "  <if test=\"deptId != null\">AND dept_id = #{deptId}</if>",
            "</where>",
            "</script>"
    })
    @Results(id = "KcapResult", value = {
            @Result(property = "kcapId", column = "kcap_id"),
            @Result(property = "kcName", column = "kc_name"),
            @Result(property = "kcQtime", column = "kc_qtime"),
            @Result(property = "kcZtime", column = "kc_ztime"),
            @Result(property = "kcDd", column = "kc_dd"),
            @Result(property = "skJs", column = "sk_js"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "deptId", column = "dept_id")
    })
    List<Kcap> selectKcapList(Kcap kcap);

    /**
     * 新增课程安排
     *
     * @param kcap 课程安排实体
     * @return 插入条数
     */
    //TODO: 向世杰 课程安排管理
    @Insert({
            "<script>",
            "INSERT INTO xscj_kcap",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "  <if test=\"kcapId != null\">kcap_id,</if>",
            "  <if test=\"kcName != null and kcName != ''\">kc_name,</if>",
            "  <if test=\"kcQtime != null\">kc_qtime,</if>",
            "  <if test=\"kcZtime != null\">kc_ztime,</if>",
            "  <if test=\"kcDd != null and kcDd != ''\">kc_dd,</if>",
            "  <if test=\"skJs != null and skJs != ''\">sk_js,</if>",
            "  <if test=\"createBy != null\">create_by,</if>",
            "  <if test=\"createTime != null\">create_time,</if>",
            "  <if test=\"updateBy != null\">update_by,</if>",
            "  <if test=\"updateTime != null\">update_time,</if>",
            "  <if test=\"userId != null\">user_id,</if>",
            "  <if test=\"deptId != null\">dept_id,</if>",
            "</trim>",
            "VALUES",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "  <if test=\"kcapId != null\">#{kcapId},</if>",
            "  <if test=\"kcName != null and kcName != ''\">#{kcName},</if>",
            "  <if test=\"kcQtime != null\">#{kcQtime},</if>",
            "  <if test=\"kcZtime != null\">#{kcZtime},</if>",
            "  <if test=\"kcDd != null and kcDd != ''\">#{kcDd},</if>",
            "  <if test=\"skJs != null and skJs != ''\">#{skJs},</if>",
            "  <if test=\"createBy != null\">#{createBy},</if>",
            "  <if test=\"createTime != null\">#{createTime},</if>",
            "  <if test=\"updateBy != null\">#{updateBy},</if>",
            "  <if test=\"updateTime != null\">#{updateTime},</if>",
            "  <if test=\"userId != null\">#{userId},</if>",
            "  <if test=\"deptId != null\">#{deptId},</if>",
            "</trim>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "kcapId")
    int insertKcap(Kcap kcap);


}