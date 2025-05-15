// Todo SJB
package com.ruoyi.xscj.kcpj.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.xscj.kcpj.mapper.KcpjMapper;
import com.ruoyi.xscj.kcpj.service.IKcpjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xscj.kcpj.domain.Kcpj;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.ruoyi.common.utils.SecurityUtils.*;

/**
 * 课程评价Service业务层处理
 *
 */
@Service
public class KcpjServiceImpl implements IKcpjService {
    @Autowired
    private KcpjMapper kcpjMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询课程评价
     *
     * @param kcpjId 课程评价主键
     * @return 课程评价
     */
    @Override
    public Kcpj selectKcpjByKcpjId(String kcpjId) {
        return kcpjMapper.selectKcpjByKcpjId(kcpjId);
    }

    /**
     * 查询课程评价列表
     *
     * @param kcpj 课程评价
     * @return 课程评价
     */
    @Override
    public List<Kcpj> selectKcpjList(Kcpj kcpj) {
        return kcpjMapper.selectKcpjList(kcpj);
    }

    /**
     * 新增课程评价
     *
     * @param kcpj 课程评价
     * @return 结果
     */
    @Override
    public int insertKcpj(Kcpj kcpj) {
        kcpj.setCreateTime(DateUtils.getNowDate());
        kcpj.setUserId(Math.toIntExact(getUserId())); //用户ID
        kcpj.setDeptId(Math.toIntExact(getDeptId())); //部门ID
        kcpj.setCreateBy(getUsername()); //创建人
        kcpj.setKcpjId(IdUtils.fastSimpleUUID());

        return kcpjMapper.insertKcpj(kcpj);
    }

    /**
     * 批量新增课程评价
     *
     * @param kcpjs 课程评价List
     * @return 结果
     */
    @Override
    public int batchInsertKcpj(List<Kcpj> kcpjs) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(kcpjs)) {
            try {
                for (int i = 0; i < kcpjs.size(); i++) {
                    int row = kcpjMapper.insertKcpj(kcpjs.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == kcpjs.size() - 1;
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
     * 修改课程评价
     *
     * @param kcpj 课程评价
     * @return 结果
     */
    @Override
    public int updateKcpj(Kcpj kcpj) {
        kcpj.setUpdateTime(DateUtils.getNowDate());
        return kcpjMapper.updateKcpj(kcpj);
    }

    /**
     * 批量删除课程评价
     *
     * @param kcpjIds 需要删除的课程评价主键
     * @return 结果
     */
    @Override
    public int deleteKcpjByKcpjIds(String[] kcpjIds) {
        return kcpjMapper.deleteKcpjByKcpjIds(kcpjIds);
    }

    /**
     * 删除课程评价信息
     *
     * @param kcpjId 课程评价主键
     * @return 结果
     */
    @Override
    public int deleteKcpjByKcpjId(String kcpjId) {
        return kcpjMapper.deleteKcpjByKcpjId(kcpjId);
    }
}
