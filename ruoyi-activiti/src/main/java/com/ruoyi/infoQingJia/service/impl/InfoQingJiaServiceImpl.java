package com.ruoyi.infoQingJia.service.impl;

import com.ruoyi.infoQingJia.domain.InfoQingJia;
import com.ruoyi.infoQingJia.mapper.InfoQingJiaMapper;
import com.ruoyi.infoQingJia.service.IInfoQingJiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 请假Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-22
 */
@Service
public class InfoQingJiaServiceImpl implements IInfoQingJiaService {
    @Resource
    private InfoQingJiaMapper infoQingJiaMapper;

    /**
     * 查询请假
     *
     * @param id 请假主键
     * @return 请假
     */
    @Override
    public InfoQingJia selectInfoQingJiaById(Long id) {
        return infoQingJiaMapper.selectInfoQingJiaById(id);
    }

    /**
     * 查询请假列表
     *
     * @param infoQingJia 请假
     * @return 请假
     */
    @Override
    public List<InfoQingJia> selectInfoQingJiaList(InfoQingJia infoQingJia) {
        return infoQingJiaMapper.selectInfoQingJiaList(infoQingJia);
    }

    /**
     * 新增请假
     *
     * @param infoQingJia 请假
     * @return 结果
     */
    @Override
    public int insertInfoQingJia(InfoQingJia infoQingJia) {
        return infoQingJiaMapper.insertInfoQingJia(infoQingJia);
    }

    /**
     * 修改请假
     *
     * @param infoQingJia 请假
     * @return 结果
     */
    @Override
    public int updateInfoQingJia(InfoQingJia infoQingJia) {
        return infoQingJiaMapper.updateInfoQingJia(infoQingJia);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假主键
     * @return 结果
     */
    @Override
    public int deleteInfoQingJiaByIds(Long[] ids) {
        return infoQingJiaMapper.deleteInfoQingJiaByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假主键
     * @return 结果
     */
    @Override
    public int deleteInfoQingJiaById(Long id) {
        return infoQingJiaMapper.deleteInfoQingJiaById(id);
    }
}
