// Todo SJB
package com.ruoyi.xscj.kcpj.service;

import java.util.List;
import com.ruoyi.xscj.kcpj.domain.Kcpj;

/**
 * 课程评价Service接口
 *
 */
public interface IKcpjService
{
    /**
     * 查询课程评价
     *
     * @param kcpjId 课程评价主键
     * @return 课程评价
     */
    public Kcpj selectKcpjByKcpjId(String kcpjId);

    /**
     * 查询课程评价列表
     *
     * @param kcpj 课程评价
     * @return 课程评价集合
     */
    public List<Kcpj> selectKcpjList(Kcpj kcpj);

    /**
     * 新增课程评价
     *
     * @param kcpj 课程评价
     * @return 结果
     */
    public int insertKcpj(Kcpj kcpj);

    /**
     * 批量新增课程评价
     *
     * @param kcpjs 课程评价List
     * @return 结果
     */
    public int batchInsertKcpj(List<Kcpj> kcpjs);

    /**
     * 修改课程评价
     *
     * @param kcpj 课程评价
     * @return 结果
     */
    public int updateKcpj(Kcpj kcpj);

    /**
     * 批量删除课程评价
     *
     * @param kcpjIds 需要删除的课程评价主键集合
     * @return 结果
     */
    public int deleteKcpjByKcpjIds(String[] kcpjIds);

    /**
     * 删除课程评价信息
     *
     * @param kcpjId 课程评价主键
     * @return 结果
     */
    public int deleteKcpjByKcpjId(String kcpjId);
}
