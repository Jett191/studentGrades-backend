// Todo WJS
// Todo LJY
// Todo SJB

package com.ruoyi.xscj.cjlr.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xscj.cjlr.domain.BjCj;
import com.ruoyi.xscj.cjlr.domain.CjTj;
import com.ruoyi.xscj.cjlr.domain.Cjlr;
import com.ruoyi.xscj.cjlr.domain.Gpa;
import com.ruoyi.xscj.cjlr.mapper.CjlrMapper;
import com.ruoyi.xscj.cjlr.service.ICjlrService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 成绩录入Service业务层处理
 *
 */
@Service
public class CjlrServiceImpl implements ICjlrService {
    @Autowired
    private CjlrMapper cjlrMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询成绩录入
     *
     * @param cjlrId 成绩录入主键
     * @return 成绩录入
     */
    @Override
    public Cjlr selectCjlrByCjlrId(String cjlrId) {
        return cjlrMapper.selectCjlrByCjlrId(cjlrId);
    }

    /**
     * 查询成绩录入列表
     *
     * @param cjlr 成绩录入
     * @return 成绩录入
     */
    @Override
    public List<Cjlr> selectCjlrList(Cjlr cjlr) {
        return cjlrMapper.selectCjlrList(cjlr);
    }

    /**
     * 新增成绩录入
     *
     * @param cjlr 成绩录入
     * @return 结果
     */
    @Override
    public int insertCjlr(Cjlr cjlr) {
        cjlr.setCreateTime(DateUtils.getNowDate());
        return cjlrMapper.insertCjlr(cjlr);
    }

    /**
     * 批量新增成绩录入
     *
     * @param cjlrs 成绩录入List
     * @return 结果
     */
    @Override
    public int batchInsertCjlr(List<Cjlr> cjlrs) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(cjlrs)) {
            try {
                for (int i = 0; i < cjlrs.size(); i++) {
                    int row = cjlrMapper.insertCjlr(cjlrs.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == cjlrs.size() - 1;
                    if (bool) {
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            } finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }

    /**
     * 修改成绩录入
     *
     * @param cjlr 成绩录入
     * @return 结果
     */
    @Override
    public int updateCjlr(Cjlr cjlr) {
        cjlr.setUpdateTime(DateUtils.getNowDate());
        return cjlrMapper.updateCjlr(cjlr);
    }

    /**
     * 批量删除成绩录入
     *
     * @param cjlrIds 需要删除的成绩录入主键
     * @return 结果
     */
    @Override
    public int deleteCjlrByCjlrIds(String[] cjlrIds) {
        return cjlrMapper.deleteCjlrByCjlrIds(cjlrIds);
    }

    /**
     * 删除成绩录入信息
     *
     * @param cjlrId 成绩录入主键
     * @return 结果
     */
    @Override
    public int deleteCjlrByCjlrId(String cjlrId) {
        return cjlrMapper.deleteCjlrByCjlrId(cjlrId);
    }

}