package com.ruoyi.infoQingJia.mapper;

import java.util.List;
import com.ruoyi.infoQingJia.domain.InfoQingJia;

/**
 * 请假Mapper接口
 * 
 * @author ruoyi
 * @date 2024-02-22
 */
public interface InfoQingJiaMapper 
{
    /**
     * 查询请假
     * 
     * @param id 请假主键
     * @return 请假
     */
    public InfoQingJia selectInfoQingJiaById(Long id);

    /**
     * 查询请假列表
     * 
     * @param infoQingJia 请假
     * @return 请假集合
     */
    public List<InfoQingJia> selectInfoQingJiaList(InfoQingJia infoQingJia);

    /**
     * 新增请假
     * 
     * @param infoQingJia 请假
     * @return 结果
     */
    public int insertInfoQingJia(InfoQingJia infoQingJia);

    /**
     * 修改请假
     * 
     * @param infoQingJia 请假
     * @return 结果
     */
    public int updateInfoQingJia(InfoQingJia infoQingJia);

    /**
     * 删除请假
     * 
     * @param id 请假主键
     * @return 结果
     */
    public int deleteInfoQingJiaById(Long id);

    /**
     * 批量删除请假
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInfoQingJiaByIds(Long[] ids);
}
