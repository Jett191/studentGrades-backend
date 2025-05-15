// Todo PJX
// Todo WJS
package com.ruoyi.xscj.jbxx.service.impl;


import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.xscj.jbxx.domain.Xsjbxx;
import com.ruoyi.xscj.jbxx.mapper.XsjbxxMapper;
import com.ruoyi.xscj.jbxx.service.IXsjbxxService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.ruoyi.system.mapper.SysUserMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.common.utils.SecurityUtils.*;

/**
 * 学生基本信息Service业务层处理
 * @author LukAsy_
 * @date 2025/4/19
 */

@Service
public class XsjbxxServiceImpl implements IXsjbxxService {
    @Resource
    private XsjbxxMapper xsjbxxMapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    /**
     * 查询学生基本信息
     *
     * @param xsjbxxId 学生基本信息主键
     * @return 学生基本信息
     */
    @Override
    public Xsjbxx selectXsjbxxByXsjbxxId(String xsjbxxId) {
        return xsjbxxMapper.selectXsjbxxByXsjbxxId(xsjbxxId);
    }

    /**
     * 查询学生基本信息列表
     *
     * @param xsjbxx 学生基本信息
     * @return 学生基本信息
     */
    @Override
    public List<Xsjbxx> selectXsjbxxList(Xsjbxx xsjbxx) {
        return xsjbxxMapper.selectXsjbxxList(xsjbxx);
    }

    /**
     * 删除学生基本信息信息
     *
     * @param xsjbxxId 学生基本信息主键
     * @return 结果
     */
    @Override
    public int deleteXsjbxxByXsjbxxId(String xsjbxxId) {
        return xsjbxxMapper.deleteXsjbxxByXsjbxxId(xsjbxxId);
    }


}
