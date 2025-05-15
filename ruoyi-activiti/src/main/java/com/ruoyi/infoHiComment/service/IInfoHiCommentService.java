package com.ruoyi.infoHiComment.service;

import java.util.List;
import com.ruoyi.infoHiComment.domain.InfoHiComment;

/**
 * 审批记录Service接口
 * 
 * @author ruoyi
 * @date 2024-03-11
 */
public interface IInfoHiCommentService 
{
    /**
     * 查询审批记录
     * 
     * @param id 审批记录主键
     * @return 审批记录
     */
    public InfoHiComment selectInfoHiCommentById(Long id);

    /**
     * 查询审批记录列表
     * 
     * @param infoHiComment 审批记录
     * @return 审批记录集合
     */
    public List<InfoHiComment> selectInfoHiCommentList(InfoHiComment infoHiComment);

    /**
     * 新增审批记录
     * 
     * @param infoHiComment 审批记录
     * @return 结果
     */
    public int insertInfoHiComment(InfoHiComment infoHiComment);

    /**
     * 修改审批记录
     * 
     * @param infoHiComment 审批记录
     * @return 结果
     */
    public int updateInfoHiComment(InfoHiComment infoHiComment);

    /**
     * 批量删除审批记录
     * 
     * @param ids 需要删除的审批记录主键集合
     * @return 结果
     */
    public int deleteInfoHiCommentByIds(Long[] ids);

    /**
     * 删除审批记录信息
     * 
     * @param id 审批记录主键
     * @return 结果
     */
    public int deleteInfoHiCommentById(Long id);
}
