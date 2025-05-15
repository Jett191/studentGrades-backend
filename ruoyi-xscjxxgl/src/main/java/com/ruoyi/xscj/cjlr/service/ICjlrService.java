// Todo WJS
// Todo LJY
// Todo SJB
package com.ruoyi.xscj.cjlr.service;

import java.util.List;

import com.ruoyi.xscj.cjlr.domain.BjCj;
import com.ruoyi.xscj.cjlr.domain.CjTj;
import com.ruoyi.xscj.cjlr.domain.Cjlr;
import com.ruoyi.xscj.cjlr.domain.Gpa;

/**
 * 成绩录入Service接口
 *
 */
public interface ICjlrService{
    /**
     * 查询成绩录入
     *
     * @param cjlrId 成绩录入主键
     * @return 成绩录入
     */
    public Cjlr selectCjlrByCjlrId(String cjlrId);

    /**
     * 查询成绩录入列表
     *
     * @param cjlr 成绩录入
     * @return 成绩录入集合
     */
    public List<Cjlr> selectCjlrList(Cjlr cjlr);

    /**
     * 新增成绩录入
     *
     * @param cjlr 成绩录入
     * @return 结果
     */
    public int insertCjlr(Cjlr cjlr);

    /**
     * 批量新增成绩录入
     *
     * @param cjlrs 成绩录入List
     * @return 结果
     */
    public int batchInsertCjlr(List<Cjlr> cjlrs);

    /**
     * 修改成绩录入
     *
     * @param cjlr 成绩录入
     * @return 结果
     */
    public int updateCjlr(Cjlr cjlr);

    /**
     * 批量删除成绩录入
     *
     * @param cjlrIds 需要删除的成绩录入主键集合
     * @return 结果
     */
    public int deleteCjlrByCjlrIds(String[] cjlrIds);

    /**
     * 删除成绩录入信息
     *
     * @param cjlrId 成绩录入主键
     * @return 结果
     */
    public int deleteCjlrByCjlrId(String cjlrId);

    /**
     * 不分页查询成绩统计列表
     * @return
     */
    List<CjTj> selectCjTjList();

    /**
     * 学生成绩分析
     * @param xsName
     * @return
     */
    List<Cjlr> selectXscjfx(String xsName);

    /**
     * 查询gpa
     * @return
     */
    List<Gpa> selectGpaList();

    /**
     * 查询班级成绩排名
     * @return
     */
    List<BjCj> selectBjCjList();

}