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
     * 新增学生基本信息
     *
     * @param xsjbxx 学生基本信息
     * @return 结果
     */
    @Override
    public int insertXsjbxx(Xsjbxx xsjbxx) {
        xsjbxx.setCreateTime(DateUtils.getNowDate()); //创建时间
        xsjbxx.setUserId(Math.toIntExact(getUserId())); //用户ID
        xsjbxx.setDeptId(Math.toIntExact(getDeptId())); //部门ID
        xsjbxx.setCreateBy(getUsername()); //创建人
        String uuid = IdUtils.fastSimpleUUID();
        xsjbxx.setXsjbxxId(uuid);
        xsjbxxMapper.insertXsjbxx(xsjbxx);
        SysUser user = new SysUser();
        user.setNickName(xsjbxx.getXm());
        user.setUserName(xsjbxx.getXh());
        user.setPassword(SecurityUtils.encryptPassword("123456"));
        user.setRoleIds(new Long[]{5L});
        sysUserMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user, uuid);
        return 1;
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user, String uuid)
    {
        System.out.println(user);
        this.insertUserRoles(user.getUserId(), user.getRoleIds());
        Xsjbxx xsjbxx = selectXsjbxxByXsjbxxId(uuid);
        Xsjbxx xsjbxx1 = new Xsjbxx();
        xsjbxx1.setXsjbxxId(uuid);
        xsjbxx1.setSsUserId(Math.toIntExact(user.getUserId()));
        updateXsjbxx(xsjbxx1);
        System.out.println(xsjbxx);
    }

    /**
     * 新增用户角色信息
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRoles(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotEmpty(roleIds))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 批量新增学生基本信息
     *
     * @param xsjbxxs 学生基本信息List
     * @return 结果
     */
    @Override
    public int batchInsertXsjbxx(List<Xsjbxx> xsjbxxs) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(xsjbxxs)) {
            try {
                for (int i = 0; i < xsjbxxs.size(); i++) {
                    int row = xsjbxxMapper.insertXsjbxx(xsjbxxs.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == xsjbxxs.size() - 1;
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
     * 修改学生基本信息
     *
     * @param xsjbxx 学生基本信息
     * @return 结果
     */
    @Override
    public int updateXsjbxx(Xsjbxx xsjbxx) {
        xsjbxx.setUpdateTime(DateUtils.getNowDate());
        return xsjbxxMapper.updateXsjbxx(xsjbxx);
    }

    /**
     * 批量删除学生基本信息
     *
     * @param xsjbxxIds 需要删除的学生基本信息主键
     * @return 结果
     */
    @Override
    public int deleteXsjbxxByXsjbxxIds(String[] xsjbxxIds) {
        return xsjbxxMapper.deleteXsjbxxByXsjbxxIds(xsjbxxIds);
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
