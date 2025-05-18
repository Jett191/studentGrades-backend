package com.ruoyi.xscj.kcxx.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import com.ruoyi.xscj.kcxx.domain.Kcxx;
import com.ruoyi.xscj.kcxx.domain.KcxxFj;
import com.ruoyi.xscj.kcxx.domain.Js;

@Mapper
public interface KcxxMapper {

  /**
   * 查询课程信息（包含附件列表）
   *
   * @param kcxxId 课程信息主键
   * @return 课程信息（带附件）
   */
  @Select("SELECT kcxx_id, kc_name, kc_dm, xf, kc_lx, create_by, create_time, update_by, update_time, user_id, dept_id ,deleted "
      + "FROM xscj_kcxx WHERE kcxx_id = #{kcxxId}")
  @Results(id = "KcxxResult", value = {
      @Result(property = "kcxxId",   column = "kcxx_id"),
      @Result(property = "kcName",   column = "kc_name"),
      @Result(property = "kcDm",     column = "kc_dm"),
      @Result(property = "xf",       column = "xf"),
      @Result(property = "kcLx",     column = "kc_lx"),
      @Result(property = "createBy", column = "create_by"),
      @Result(property = "createTime", column = "create_time"),
      @Result(property = "updateBy",   column = "update_by"),
      @Result(property = "updateTime", column = "update_time"),
      @Result(property = "userId",     column = "user_id"),
      @Result(property = "deptId",     column = "dept_id"),
      @Result(property = "deleted", column = "deleted"),
      @Result(property = "kcxxFjList", column = "kcxx_id",
          many = @Many(select = "com.ruoyi.xscj.kcxx.mapper.KcxxMapper.selectKcxxFjList"))
  })
  Kcxx selectKcxxByKcxxId(@Param("kcxxId") String kcxxId);

  /**
   * 查询课程信息列表
   *
   * @param kcxx 课程信息实体，用于动态过滤
   * @return 课程信息集合
   */
  @Select({
      "<script>",
      "SELECT kcxx_id, kc_name, kc_dm, xf, kc_lx, create_by, create_time, update_by, update_time, user_id, dept_id, deleted",
      "FROM xscj_kcxx",
      "<where>",
      "  <if test=\"kcName != null and kcName != ''\">AND kc_name LIKE CONCAT('%', #{kcName}, '%')</if>",
      "  <if test=\"kcDm   != null and kcDm   != ''\">AND kc_dm   LIKE CONCAT('%', #{kcDm},   '%')</if>",
      "  <if test=\"xf     != null\">AND xf = #{xf}</if>",
      "  <if test=\"kcLx  != null and kcLx  != ''\">AND kc_lx  = #{kcLx}</if>",
      "  <if test=\"userId!= null\">AND user_id = #{userId}</if>",
      "  <if test=\"deptId!= null\">AND dept_id = #{deptId}</if>",
      "  <if test=\"deleted != null\">AND deleted = 0 </if>",
      "</where>",
      "</script>"
  })
  @ResultMap("KcxxResult")
  List<Kcxx> selectKcxxList(Kcxx kcxx);

  @Select("SELECT COUNT(1) FROM xscj_kcxx WHERE kcxx_id = #{kcxxId} AND deleted = 0")
  int checkKcxxExists(@Param("kcxxId") String kcxxId);


  @Select("SELECT kcxx_id, kc_name, kc_dm, xf, kc_lx, create_by, create_time, update_by, update_time, user_id, dept_id, deleted " +
      "FROM xscj_kcxx WHERE kc_name = #{kcName} AND deleted = 0 LIMIT 1")
  @ResultMap("KcxxResult")
  Kcxx selectKcxxByKcName(@Param("kcName") String kcName);


  /**
   * 新增课程信息
   *
   * @param kcxx 课程信息实体
   * @return 插入条数
   */
  @Insert({
      "<script>",
      "INSERT INTO xscj_kcxx",
      "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
      "  <if test=\"kcxxId != null\">kcxx_id,</if>",
      "  <if test=\"kcName != null and kcName != ''\">kc_name,</if>",
      "  <if test=\"kcDm   != null and kcDm   != ''\">kc_dm,</if>",
      "  <if test=\"xf     != null\">xf,</if>",
      "  <if test=\"kcLx  != null and kcLx  != ''\">kc_lx,</if>",
      "  <if test=\"createBy != null\">create_by,</if>",
      "  <if test=\"createTime != null\">create_time,</if>",
      "  <if test=\"updateBy != null\">update_by,</if>",
      "  <if test=\"updateTime != null\">update_time,</if>",
      "  <if test=\"userId   != null\">user_id,</if>",
      "  <if test=\"deptId   != null\">dept_id,</if>",
      "</trim>",
      "VALUES",
      "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
      "  <if test=\"kcxxId != null\">#{kcxxId},</if>",
      "  <if test=\"kcName != null and kcName != ''\">#{kcName},</if>",
      "  <if test=\"kcDm   != null and kcDm   != ''\">#{kcDm},</if>",
      "  <if test=\"xf     != null\">#{xf},</if>",
      "  <if test=\"kcLx  != null and kcLx  != ''\">#{kcLx},</if>",
      "  <if test=\"createBy != null\">#{createBy},</if>",
      "  <if test=\"createTime != null\">#{createTime},</if>",
      "  <if test=\"updateBy != null\">#{updateBy},</if>",
      "  <if test=\"updateTime != null\">#{updateTime},</if>",
      "  <if test=\"userId   != null\">#{userId},</if>",
      "  <if test=\"deptId   != null\">#{deptId},</if>",
      "</trim>",
      "</script>"
  })
  int insertKcxx(Kcxx kcxx);

  /**
   * 修改课程信息
   *
   * @param kcxx 课程信息实体
   * @return 更新条数
   */
  @Update({
      "<script>",
      "UPDATE xscj_kcxx",
      "<set>",
      "  <if test=\"kcName != null and kcName != ''\">kc_name   = #{kcName},</if>",
      "  <if test=\"kcDm   != null and kcDm   != ''\">kc_dm     = #{kcDm},</if>",
      "  <if test=\"xf     != null\">xf        = #{xf},</if>",
      "  <if test=\"kcLx  != null and kcLx  != ''\">kc_lx     = #{kcLx},</if>",
      "  <if test=\"createBy != null\">create_by  = #{createBy},</if>",
      "  <if test=\"createTime != null\">create_time= #{createTime},</if>",
      "  <if test=\"updateBy != null\">update_by  = #{updateBy},</if>",
      "  <if test=\"updateTime != null\">update_time= #{updateTime},</if>",
      "  <if test=\"userId   != null\">user_id   = #{userId},</if>",
      "  <if test=\"deptId   != null\">dept_id   = #{deptId},</if>",
      "</set>",
      "WHERE kcxx_id = #{kcxxId}",
      "</script>"
  })
  int updateKcxx(Kcxx kcxx);

  /**
   * 删除课程信息
   *
   * @param kcxxId 课程信息主键
   * @return 删除条数
   */
  @Update("UPDATE xscj_kcxx SET deleted = 1 WHERE kcxx_id = #{kcxxId}")
  int deleteKcxxByKcxxId(@Param("kcxxId") String kcxxId);


  /**
   * 批量删除课程信息
   *
   * @param kcxxIds 需要删除的主键集合
   * @return 删除条数
   */
  @Update({
      "<script>",
      "UPDATE xscj_kcxx SET deleted = 1 WHERE kcxx_id IN",
      "<foreach item=\"id\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">",
      "  #{id}",
      "</foreach>",
      "</script>"
  })
  int deleteKcxxByKcxxIds(@Param("array") String[] kcxxIds);


  /**
   * 不分页查询课程列表
   *
   * @return 课程主键与名称集合
   */
  @Select("SELECT kcxx_id AS kcxxId, kc_name AS kcName FROM xscj_kcxx")
  @ResultMap("KcxxResult")
  List<Kcxx> selectKcList();

  /**
   * 不分页查询教师列表
   *
   * @return 教师用户 ID 与姓名集合
   */
  @Select({
      "SELECT u.user_id AS userId, u.user_name AS userName",
      "FROM sys_user u",
      "JOIN sys_user_role ur ON u.user_id = ur.user_id",
      "JOIN sys_role r ON ur.role_id = r.role_id",
      "WHERE r.role_name = '教师' AND u.del_flag = 0"
  })
  List<Js> selectJsList();

  /**
   * 不分页查询学生列表
   *
   * @return 学生用户 ID 与昵称集合
   */
  @Select({
      "SELECT u.user_id AS userId, u.nick_name AS userName",
      "FROM sys_user u",
      "JOIN sys_user_role ur ON u.user_id = ur.user_id",
      "JOIN sys_role r ON ur.role_id = r.role_id",
      "WHERE r.role_name = '学生' AND u.del_flag = 0"
  })
  List<Js> selectXsList();

  /**
   * 批量删除课程附件
   *
   * @param kcxxIds 需要删除的课程主键集合
   * @return 删除条数
   */
  @Delete({
          "<script>",
          "DELETE FROM xscj_kcxx_fj WHERE kcxx_id IN",
          "<foreach item=\"id\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">",
          "  #{id}",
          "</foreach>",
          "</script>"
  })
  int deleteKcxxFjByKcxxIds(@Param("array") String[] kcxxIds);

  /**
   * 通过课程主键删除单个附件
   *
   * @param kcxxId 课程主键
   * @return 删除条数
   */
  @Update("UPDATE xscj_kcxx_fj SET deleted = 1 WHERE kcxx_id = #{kcxxId}")
  int deleteKcxxFjByKcxxId(@Param("kcxxId") String kcxxId);


  /**
   * 批量删除附件
   *
   * @param fjs 附件 ID 数组
   * @return 删除条数
   */
  @Delete({
          "<script>",
          "DELETE FROM xscj_kcxx_fj WHERE fj IN",
          "<foreach item=\"fj\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">",
          "  #{fj}",
          "</foreach>",
          "</script>"
  })
  int deleteKcxxFjByFjs(@Param("array") Integer[] fjs);

  /**
   * 批量新增课程附件
   *
   * @param list 附件列表
   * @return 插入条数
   */
  @Insert({
          "<script>",
          "INSERT INTO xscj_kcxx_fj(fj, kcxx_id, file_name, file_size) VALUES",
          "<foreach item=\"item\" collection=\"list\" separator=\",\">",
          "  (#{item.fj}, #{item.kcxxId}, #{item.fileName}, #{item.fileSize})",
          "</foreach>",
          "</script>"
  })
  int batchKcxxFj(List<KcxxFj> list);

  /**
   * 新增单个附件
   *
   * @param fj 附件实体
   * @return 插入条数
   */
  @Insert({
          "<script>",
          "INSERT INTO xscj_kcxx_fj",
          "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
          "  <if test=\"kcxxId != null and kcxxId != ''\">kcxx_id,</if>",
          "  <if test=\"fileName != null\">file_name,</if>",
          "  <if test=\"fileSize != null\">file_size,</if>",
          "</trim>",
          "VALUES",
          "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
          "  <if test=\"kcxxId != null and kcxxId != ''\">#{kcxxId},</if>",
          "  <if test=\"fileName != null\">#{fileName},</if>",
          "  <if test=\"fileSize != null\">#{fileSize},</if>",
          "</trim>",
          "</script>"
  })
  @Options(useGeneratedKeys = true, keyProperty = "fj")
  int insertKcxxFjByKcxxId(KcxxFj fj);

  /**
   * 查询附件列表
   *
   * @param filter 过滤条件实体（只用 kcxxId、fileName、fileSize）
   * @return 附件集合
   */
  @Select({
          "<script>",
          "SELECT fj, kcxx_id AS kcxxId, file_name AS fileName, file_size AS fileSize",
          "FROM xscj_kcxx_fj",
          "<where>",
          "  <if test=\"kcxxId != null and kcxxId != ''\">AND kcxx_id = #{kcxxId}</if>",
          "  <if test=\"fileName != null and fileName != ''\">AND file_name LIKE CONCAT('%', #{fileName}, '%')</if>",
          "  <if test=\"fileSize != null\">AND file_size = #{fileSize}</if>",
          "</where>",
          "</script>"
  })
  @Results(id = "KcxxFjResult", value = {
          @Result(property = "fj",       column = "fj"),
          @Result(property = "kcxxId",   column = "kcxx_id"),
          @Result(property = "fileName", column = "file_name"),
          @Result(property = "fileSize", column = "file_size")
  })
  List<KcxxFj> selectKcxxFjList(KcxxFj filter);




}


