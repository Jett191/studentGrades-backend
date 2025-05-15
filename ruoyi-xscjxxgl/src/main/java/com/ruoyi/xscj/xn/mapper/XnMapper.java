// Todo PJX
package com.ruoyi.xscj.xn.mapper;

import com.ruoyi.xscj.xn.domain.Xn;
import com.ruoyi.xscj.xn.domain.XnKc;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface XnMapper {


    /**------------------ 学年列表（不带课程） ------------------**/
    @Select("<script>"
            + "SELECT xn_id, ks_nf, js_nf, xq "
            + "FROM xscj_xn "
            + "<where> "
            + "  <if test='ksNf != null'>AND ks_nf = #{ksNf}</if> "
            + "  <if test='jsNf != null'>AND js_nf = #{jsNf}</if> "
            + "</where>"
            + "</script>")
    @Results({
            @Result(column="xn_id", property="xnId"),
            @Result(column="ks_nf", property="ksNf"),
            @Result(column="js_nf", property="jsNf"),
            @Result(column="xq",     property="xq")
    })
    List<Xn> selectXnList(Xn xn);

    /**------------------ 查询单个学年 + 嵌套课程 ------------------**/
    @Select("SELECT xn_id, ks_nf, js_nf, xq FROM xscj_xn WHERE xn_id = #{xnId}")
    @Results({
            @Result(column = "xn_id", property = "xnId"),
            @Result(column = "ks_nf", property = "ksNf"),
            @Result(column = "js_nf", property = "jsNf"),
            @Result(column = "xq",    property = "xq"),
            @Result(
                    property = "xnKcList",
                    column   = "xn_id",
                    javaType = List.class,
                    many     = @Many(
                            select    = "selectXnKcByXnId",
                            fetchType = FetchType.LAZY
                    )
            )
    })
    Xn selectXnByXnId(@Param("xnId") String xnId);

    /**------------------ 某学年的所有课程 ------------------**/
    @Select("SELECT kc_id, xn_id, kch, kcm, kc_xf, rk_ls " +
            "FROM xscj_xn_kc WHERE xn_id = #{xnId}")
    @Results({
            @Result(column = "kc_id", property = "kcId"),
            @Result(column = "xn_id", property = "xnId"),
            @Result(column = "kch",   property = "kch"),
            @Result(column = "kcm",   property = "kcm"),
            @Result(column = "kc_xf", property = "kcXf"),
            @Result(column = "rk_ls", property = "rkLs")
    })
    List<XnKc> selectXnKcByXnId(@Param("xnId") String xnId);

    /**------------------ 新增学年 ------------------**/
    @Insert("INSERT INTO xscj_xn(xn_id, ks_nf, js_nf, xq) " +
            "VALUES(#{xnId}, #{ksNf}, #{jsNf}, #{xq})")
    int insertXn(Xn xn);

    /**------------------ 修改学年 ------------------**/
    @Update("<script>"
            + "UPDATE xscj_xn "
            + "<set>"
            + "  <if test='ksNf != null'>ks_nf = #{ksNf},</if>"
            + "  <if test='jsNf != null'>js_nf = #{jsNf},</if>"
            + "  <if test='xq   != null'>xq    = #{xq},</if>"
            + "</set>"
            + "WHERE xn_id = #{xnId}"
            + "</script>")
    int updateXn(Xn xn);

    /**------------------ 删除单个学年 ------------------**/
    @Delete("DELETE FROM xscj_xn WHERE xn_id = #{xnId}")
    int deleteXnByXnId(@Param("xnId") String xnId);

    /**------------------ 批量删除学年 ------------------**/
    @Delete("<script>"
            + "DELETE FROM xscj_xn WHERE xn_id IN "
            + "<foreach collection='array' item='id' open='(' separator=',' close=')'>"
            + "  #{id}"
            + "</foreach>"
            + "</script>")
    int deleteXnByXnIds(@Param("array") String[] xnIds);

    /**------------------ 删除某学年的所有课程 ------------------**/
    @Delete("DELETE FROM xscj_xn_kc WHERE xn_id = #{xnId}")
    int deleteXnKcByXnId(@Param("xnId") String xnId);

    /**------------------ 批量删除多学年的课程 ------------------**/
    @Delete("<script>"
            + "DELETE FROM xscj_xn_kc WHERE xn_id IN "
            + "<foreach collection='array' item='id' open='(' separator=',' close=')'>"
            + "  #{id}"
            + "</foreach>"
            + "</script>")
    int deleteXnKcByXnIds(@Param("array") String[] xnIds);

    /**------------------ 批量新增学年-课程关联 （kc_id 自增） ------------------**/
    @Options(useGeneratedKeys = true, keyProperty = "kcId")
    @Insert("<script>"
            + "INSERT INTO xscj_xn_kc(xn_id, kch, kcm, kc_xf, rk_ls) VALUES "
            + "<foreach collection='list' item='item' separator=','>"
            + "  (#{item.xnId}, #{item.kch}, #{item.kcm}, #{item.kcXf}, #{item.rkLs})"
            + "</foreach>"
            + "</script>")
    int batchXnKc(@Param("list") List<XnKc> xnKcList);

}
