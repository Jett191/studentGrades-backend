
// Todo XSJ
package com.ruoyi.xscj.kcap.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.xscj.kcap.domain.Kcap;
import com.ruoyi.xscj.kcap.mapper.KcapMapper;
import com.ruoyi.xscj.kcap.service.IKcapService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.ruoyi.common.utils.SecurityUtils.*;

/**
 * 课程安排Service业务层处理
 *
 */
@Service
public class KcapServiceImpl implements IKcapService {
    @Resource
    private KcapMapper kcapMapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询课程安排
     *
     * @param kcapId 课程安排主键
     * @return 课程安排
     */
    //TODO: 向世杰 课程安排管理
    @Override
    public Kcap selectKcapByKcapId(String kcapId) {
        return kcapMapper.selectKcapByKcapId(kcapId);
    }

    /**
     * 查询课程安排列表
     *
     * @param kcap 课程安排
     * @return 课程安排
     */
    //TODO: 向世杰 课程安排管理
    @Override
    public List<Kcap> selectKcapList(Kcap kcap) {
        return kcapMapper.selectKcapList(kcap);
    }

    /**
     * 新增课程安排
     *
     * @param kcap 课程安排
     * @return 结果
     */
    //TODO: 向世杰 课程安排管理
    @Override
    public int insertKcap(Kcap kcap) {
        kcap.setCreateTime(DateUtils.getNowDate());
        kcap.setUserId(Math.toIntExact(getUserId())); //用户ID
        kcap.setDeptId(Math.toIntExact(getDeptId())); //部门ID
        kcap.setCreateBy(getUsername()); //创建人
        kcap.setKcapId(IdUtils.fastSimpleUUID());
        return kcapMapper.insertKcap(kcap);
    }

    /**
     * 批量新增课程安排
     *
     * @param kcaps 课程安排List
     * @return 结果
     */
    //TODO: 向世杰 课程安排管理
    @Override
    public int batchInsertKcap(List<Kcap> kcaps) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(kcaps)) {
            try {
                for (int i = 0; i < kcaps.size(); i++) {
                    int row = kcapMapper.insertKcap(kcaps.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == kcaps.size() - 1;
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


}
