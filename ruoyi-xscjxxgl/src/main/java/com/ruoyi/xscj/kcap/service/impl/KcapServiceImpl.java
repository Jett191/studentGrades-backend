// Todo SJB
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
     * 修改课程安排
     *
     * @param kcap 课程安排
     * @return 结果
     */
    //TODO：邵靖彬  课程冲突检测
    @Override
    public int updateKcap(Kcap kcap) {
        kcap.setUpdateTime(DateUtils.getNowDate());
        return kcapMapper.updateKcap(kcap);
    }

    /**
     * 批量删除课程安排
     *
     * @param kcapIds 需要删除的课程安排主键
     * @return 结果
     */
    //TODO：邵靖彬  课程冲突检测
    @Override
    public int deleteKcapByKcapIds(String[] kcapIds) {
        return kcapMapper.deleteKcapByKcapIds(kcapIds);
    }

    /**
     * 删除课程安排信息
     *
     * @param kcapId 课程安排主键
     * @return 结果
     */
    //TODO：邵靖彬 课程冲突检测
    @Override
    public int deleteKcapByKcapId(String kcapId) {
        return kcapMapper.deleteKcapByKcapId(kcapId);
    }
}
