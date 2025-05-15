package com.ruoyi.infoHiComment.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.infoHiComment.mapper.InfoHiCommentMapper;
import com.ruoyi.infoHiComment.domain.InfoHiComment;
import com.ruoyi.infoHiComment.service.IInfoHiCommentService;

import javax.annotation.Resource;

/**
 * 审批记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-11
 */
@Service
public class InfoHiCommentServiceImpl implements IInfoHiCommentService
{
    @Resource
    private InfoHiCommentMapper infoHiCommentMapper;

    /**
     * 查询审批记录
     *
     * @param id 审批记录主键
     * @return 审批记录
     */
    @Override
    public InfoHiComment selectInfoHiCommentById(Long id)
    {
        return infoHiCommentMapper.selectInfoHiCommentById(id);
    }

    /**
     * 查询审批记录列表
     *
     * @param infoHiComment 审批记录
     * @return 审批记录
     */
    @Override
    public List<InfoHiComment> selectInfoHiCommentList(InfoHiComment infoHiComment)
    {
        return infoHiCommentMapper.selectInfoHiCommentList(infoHiComment);
    }

    /**
     * 新增审批记录
     *
     * @param infoHiComment 审批记录
     * @return 结果
     */
    @Override
    public int insertInfoHiComment(InfoHiComment infoHiComment)
    {
        infoHiComment.setCreateTime(DateUtils.getNowDate());
        return infoHiCommentMapper.insertInfoHiComment(infoHiComment);
    }

    /**
     * 修改审批记录
     *
     * @param infoHiComment 审批记录
     * @return 结果
     */
    @Override
    public int updateInfoHiComment(InfoHiComment infoHiComment)
    {
        return infoHiCommentMapper.updateInfoHiComment(infoHiComment);
    }

    /**
     * 批量删除审批记录
     *
     * @param ids 需要删除的审批记录主键
     * @return 结果
     */
    @Override
    public int deleteInfoHiCommentByIds(Long[] ids)
    {
        return infoHiCommentMapper.deleteInfoHiCommentByIds(ids);
    }

    /**
     * 删除审批记录信息
     *
     * @param id 审批记录主键
     * @return 结果
     */
    @Override
    public int deleteInfoHiCommentById(Long id)
    {
        return infoHiCommentMapper.deleteInfoHiCommentById(id);
    }
}
