// Todo SJB
// Todo XSJ
package com.ruoyi.xscj.kcap.service;

import java.util.List;
import com.ruoyi.xscj.kcap.domain.Kcap;

/**
 * 课程安排Service接口
 *
 */

public interface IKcapService
{
    /**
     * 修改课程安排
     *
     * @param kcap 课程安排
     * @return 结果
     */
    //TODO: 邵靖彬 课程冲突检测
    public int updateKcap(Kcap kcap);

    /**
     * 批量删除课程安排
     *
     * @param kcapIds 需要删除的课程安排主键集合
     * @return 结果
     */
    //TODO: 邵靖彬 课程冲突检测
    public int deleteKcapByKcapIds(String[] kcapIds);

    /**
     * 删除课程安排信息
     *
     * @param kcapId 课程安排主键
     * @return 结果
     */
    //TODO: 邵靖彬 课程冲突检测
    public int deleteKcapByKcapId(String kcapId);


    /**
     * 查询课程安排
     *
     * @param kcapId 课程安排主键
     * @return 课程安排
     */
    //TODO: 向世杰 课程安排管理
    public Kcap selectKcapByKcapId(String kcapId);

    /**
     * 查询课程安排列表
     *
     * @param kcap 课程安排
     * @return 课程安排集合
     */
    //TODO: 向世杰 课程安排管理
    public List<Kcap> selectKcapList(Kcap kcap);

    /**
     * 新增课程安排
     *
     * @param kcap 课程安排
     * @return 结果
     */
    //TODO: 向世杰 课程安排管理
    public int insertKcap(Kcap kcap);

    /**
     * 批量新增课程安排
     *
     * @param kcaps 课程安排List
     * @return 结果
     */
    //TODO: 向世杰 课程安排管理
    public int batchInsertKcap(List<Kcap> kcaps);



}
