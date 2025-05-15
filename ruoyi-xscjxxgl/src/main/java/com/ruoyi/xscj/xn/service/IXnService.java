// Todo PJX
package com.ruoyi.xscj.xn.service;


import com.ruoyi.xscj.xn.domain.Xn;

import java.util.List;

/**
 * 学年Service接口
 *
 *
 * @date 2025-03-05
 */
public interface IXnService
{
    /**
     * 查询学年
     *
     * @param xnId 学年主键
     * @return 学年
     */
    public Xn selectXnByXnId(String xnId);

    /**
     * 查询学年列表
     *
     * @param xn 学年
     * @return 学年集合
     */
    public List<Xn> selectXnList(Xn xn);

    /**
     * 新增学年
     *
     * @param xn 学年
     * @return 结果
     */
    public int insertXn(Xn xn);

    /**
     * 批量新增学年
     *
     * @param xns 学年List
     * @return 结果
     */
    public int batchInsertXn(List<Xn> xns);

    /**
     * 修改学年
     *
     * @param xn 学年
     * @return 结果
     */
    public int updateXn(Xn xn);

    /**
     * 批量删除学年
     *
     * @param xnIds 需要删除的学年主键集合
     * @return 结果
     */
    public int deleteXnByXnIds(String[] xnIds);

    /**
     * 删除学年信息
     *
     * @param xnId 学年主键
     * @return 结果
     */
    public int deleteXnByXnId(String xnId);
}