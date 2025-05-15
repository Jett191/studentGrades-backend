// Todo SJB
package com.ruoyi.xscj.cjxgsp.service;

import java.util.List;
import com.ruoyi.xscj.cjxgsp.domain.Cjxgsp;

/**
 * 成绩修改审批Service接口
 * @author sjba1
 * @date 2025/4/25
 */
public interface ICjxgspService
{
    /**
     * 查询成绩修改审批
     *
     * @param cjxgspId 成绩修改审批主键
     * @return 成绩修改审批
     */
    public Cjxgsp selectCjxgspByCjxgspId(Integer cjxgspId);

    /**
     * 查询成绩修改审批列表
     *
     * @param cjxgsp 成绩修改审批
     * @return 成绩修改审批集合
     */
    public List<Cjxgsp> selectCjxgspList(Cjxgsp cjxgsp);

    /**
     * 新增成绩修改审批
     *
     * @param cjxgsp 成绩修改审批
     * @return 结果
     */
    public int insertCjxgsp(Cjxgsp cjxgsp);

    /**
     * 批量新增成绩修改审批
     *
     * @param cjxgsps 成绩修改审批List
     * @return 结果
     */
    public int batchInsertCjxgsp(List<Cjxgsp> cjxgsps);

    /**
     * 修改成绩修改审批
     *
     * @param cjxgsp 成绩修改审批
     * @return 结果
     */
    public int updateCjxgsp(Cjxgsp cjxgsp);

    /**
     * 批量删除成绩修改审批
     *
     * @param cjxgspIds 需要删除的成绩修改审批主键集合
     * @return 结果
     */
    public int deleteCjxgspByCjxgspIds(Integer[] cjxgspIds);

    /**
     * 删除成绩修改审批信息
     *
     * @param cjxgspId 成绩修改审批主键
     * @return 结果
     */
    public int deleteCjxgspByCjxgspId(Integer cjxgspId);
}
