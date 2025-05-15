// Todo PJX
package com.ruoyi.xscj.xn.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xscj.xn.domain.Xn;
import com.ruoyi.xscj.xn.domain.XnKc;
import com.ruoyi.xscj.xn.mapper.XnMapper;
import com.ruoyi.xscj.xn.service.IXnService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class XnServiceImpl implements IXnService {
    @Autowired
    private XnMapper xnMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询学年
     *
     * @param xnId 学年主键
     * @return 学年
     */
    @Override
    public Xn selectXnByXnId(String xnId) {
        Xn xn = xnMapper.selectXnByXnId(xnId);
        if (xn == null) {
            return null;
        }
        // 避免 null，前端拿到就能直接 .push()
        if (xn.getXnKcList() == null) {
            xn.setXnKcList(Collections.emptyList());
        }
        return xn;
    }

    /**
     * 查询学年列表
     *
     * @param xn 学年
     * @return 学年
     */
    @Override
    public List<Xn> selectXnList(Xn xn) {
        return xnMapper.selectXnList(xn);
    }

    /**
     * 新增学年
     *
     * @param xn 学年
     * @return 结果
     */
    @Transactional
    @Override
    public int insertXn(Xn xn) {
        int rows = xnMapper.insertXn(xn);
        insertXnKc(xn);
        return rows;
    }

    /**
     * 批量新增学年
     *
     * @param xns 学年List
     * @return 结果
     */
    @Override
    public int batchInsertXn(List<Xn> xns) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(xns)) {
            try {
                for (int i = 0; i < xns.size(); i++) {
                    int row = xnMapper.insertXn(xns.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == xns.size() - 1;
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
     * 修改学年
     *
     * @param xn 学年
     * @return 结果
     */
    @Transactional
    @Override
    public int updateXn(Xn xn) {
        xnMapper.deleteXnKcByXnId(xn.getXnId());
        insertXnKc(xn);
        return xnMapper.updateXn(xn);
    }

    /**
     * 批量删除学年
     *
     * @param xnIds 需要删除的学年主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteXnByXnIds(String[] xnIds) {
        xnMapper.deleteXnKcByXnIds(xnIds);
        return xnMapper.deleteXnByXnIds(xnIds);
    }

    /**
     * 删除学年信息
     *
     * @param xnId 学年主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteXnByXnId(String xnId) {
        xnMapper.deleteXnKcByXnId(xnId);
        return xnMapper.deleteXnByXnId(xnId);
    }

    /**
     * 新增课程信息
     *
     * @param xn 学年对象
     */
    public void insertXnKc(Xn xn) {
        List<XnKc> xnKcList = xn.getXnKcList();
        String xnId = xn.getXnId();
        if (StringUtils.isNotNull(xnKcList)) {
            List<XnKc> list = new ArrayList<XnKc>();
            for (XnKc xnKc : xnKcList) {
                xnKc.setXnId(xnId);
                list.add(xnKc);
            }
            if (list.size() > 0) {
                xnMapper.batchXnKc(list);
            }
        }
    }
}
