package com.ruoyi.xscj.kcxx.service;

import java.util.List;

import com.ruoyi.xscj.kcxx.domain.Js;
import com.ruoyi.xscj.kcxx.domain.Kcxx;
import com.ruoyi.xscj.kcxx.domain.KcxxFj;

/**
 * 课程信息Service接口
 *
 */
public interface IKcxxService
{
  /**
   * 查询课程信息
   *
   * @param kcxxId 课程信息主键
   * @return 课程信息
   */
  public Kcxx selectKcxxByKcxxId(String kcxxId);

  /**
   * 查询课程信息列表
   */
  public List<Kcxx> selectKcxxList(Kcxx kcxx);

  /**
   * 新增课程信息
   *
   * @param kcxx 课程信息
   * @return 结果
   */
  public int insertKcxx(Kcxx kcxx);

  /**
   * 批量新增课程信息
   *
   * @param kcxxs 课程信息List
   * @return 结果
   */
  public int batchInsertKcxx(List<Kcxx> kcxxs);

  /**
   * 修改课程信息
   *
   * @param kcxx 课程信息
   * @return 结果
   */
  public int updateKcxx(Kcxx kcxx);

  /**
   * 批量删除课程信息
   *
   * @param kcxxIds 需要删除的课程信息主键集合
   * @return 结果
   */
  public int deleteKcxxByKcxxIds(String[] kcxxIds);

  /**
   * 删除课程信息信息
   *
   * @param kcxxId 课程信息主键
   * @return 结果
   */
  public int deleteKcxxByKcxxId(String kcxxId);

  /**
   * 不分页查询课程列表
   * @return
   */
  List<Kcxx> selectKcList();

  /**
   * 不分页查询教师列表
   * @return
   */
  List<Js> selectJsList();

  /**
   * 不分页查询学生列表
   * @return
   */
  List<Js> selectXsList();
}
