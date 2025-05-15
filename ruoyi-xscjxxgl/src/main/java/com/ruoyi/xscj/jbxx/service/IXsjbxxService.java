// Todo PJX
// Todo WJS
package com.ruoyi.xscj.jbxx.service;

import java.util.List;

import com.ruoyi.xscj.jbxx.domain.Xsjbxx;

/**
 * 学生基本信息Service接口
 * @author LukAsy_
 * @date 2025/4/19
 */
public interface IXsjbxxService
{
    /**
     * 查询学生基本信息
     *
     * @param xsjbxxId 学生基本信息主键
     * @return 学生基本信息
     */
    public Xsjbxx selectXsjbxxByXsjbxxId(String xsjbxxId);

    /**
     * 查询学生基本信息列表
     *
     * @param xsjbxx 学生基本信息
     * @return 学生基本信息集合
     */
    public List<Xsjbxx> selectXsjbxxList(Xsjbxx xsjbxx);

    /**
     * 删除学生基本信息信息
     *
     * @param xsjbxxId 学生基本信息主键
     * @return 结果
     */
    public int deleteXsjbxxByXsjbxxId(String xsjbxxId);


}
