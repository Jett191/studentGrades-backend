// Todo SJB
package com.ruoyi.xscj.cjxgsp.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xscj.cjxgsp.domain.Cjxgsp;
import com.ruoyi.xscj.cjxgsp.mapper.CjxgspMapper;
import com.ruoyi.xscj.cjxgsp.service.ICjxgspService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.ruoyi.common.utils.SecurityUtils.getUserId;
import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 成绩修改审批Service业务层处理
 * @author sjba1
 * @date 2025/4/25
 */
@Service
public class CjxgspServiceImpl implements ICjxgspService {
    @Autowired
    private CjxgspMapper cjxgspMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询成绩修改审批
     *
     * @param cjxgspId 成绩修改审批主键
     * @return 成绩修改审批
     */
    @Override
    public Cjxgsp selectCjxgspByCjxgspId(Integer cjxgspId) {
        return cjxgspMapper.selectCjxgspByCjxgspId(cjxgspId);
    }

    /**
     * 查询成绩修改审批列表
     *
     * @param cjxgsp 成绩修改审批
     * @return 成绩修改审批
     */
    @Override
    public List<Cjxgsp> selectCjxgspList(Cjxgsp cjxgsp) {
        return cjxgspMapper.selectCjxgspList(cjxgsp);
    }

    /**
     * 新增成绩修改审批
     *
     * @param cjxgsp 成绩修改审批
     * @return 结果
     */
    @Override
    public int insertCjxgsp(Cjxgsp cjxgsp) {
        cjxgsp.setCreateTime(DateUtils.getNowDate());
        cjxgsp.setUserId(Math.toIntExact(getUserId())); //用户ID
        cjxgsp.setCreateBy(getUsername()); //创建人
        return cjxgspMapper.insertCjxgsp(cjxgsp);
    }

    /**
     * 批量新增成绩修改审批
     *
     * @param cjxgsps 成绩修改审批List
     * @return 结果
     */
    @Override
    public int batchInsertCjxgsp(List<Cjxgsp> cjxgsps) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(cjxgsps)) {
            try {
                for (int i = 0; i < cjxgsps.size(); i++) {
                    int row = cjxgspMapper.insertCjxgsp(cjxgsps.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == cjxgsps.size() - 1;
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
     * 修改成绩修改审批
     *
     * @param cjxgsp 成绩修改审批
     * @return 结果
     */
    @Override
    public int updateCjxgsp(Cjxgsp cjxgsp) {
        cjxgsp.setUpdateTime(DateUtils.getNowDate());
        return cjxgspMapper.updateCjxgsp(cjxgsp);
    }

    /**
     * 批量删除成绩修改审批
     *
     * @param cjxgspIds 需要删除的成绩修改审批主键
     * @return 结果
     */
    @Override
    public int deleteCjxgspByCjxgspIds(Integer[] cjxgspIds) {
        return cjxgspMapper.deleteCjxgspByCjxgspIds(cjxgspIds);
    }

    /**
     * 删除成绩修改审批信息
     *
     * @param cjxgspId 成绩修改审批主键
     * @return 结果
     */
    @Override
    public int deleteCjxgspByCjxgspId(Integer cjxgspId) {
        return cjxgspMapper.deleteCjxgspByCjxgspId(cjxgspId);
    }
}
