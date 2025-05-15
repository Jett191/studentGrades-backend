package com.ruoyi.infoRunTaskData.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.infoRunTaskData.mapper.InfoRunTaskDataMapper;
import com.ruoyi.infoRunTaskData.domain.InfoRunTaskData;
import com.ruoyi.infoRunTaskData.service.IInfoRunTaskDataService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 正在运行任务的业务数据Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-22
 */
@Service
public class InfoRunTaskDataServiceImpl implements IInfoRunTaskDataService {
    @Autowired
    private InfoRunTaskDataMapper infoRunTaskDataMapper;

    /**
     * 查询正在运行任务的业务数据
     *
     * @param id 正在运行任务的业务数据主键
     * @return 正在运行任务的业务数据
     */
    @Override
    public InfoRunTaskData selectInfoRunTaskDataById(Long id) {
        return infoRunTaskDataMapper.selectInfoRunTaskDataById(id);
    }

    /**
     * 查询正在运行任务的业务数据列表
     *
     * @param infoRunTaskData 正在运行任务的业务数据
     * @return 正在运行任务的业务数据
     */
    @Override
    public List<InfoRunTaskData> selectInfoRunTaskDataList(InfoRunTaskData infoRunTaskData) {
        return infoRunTaskDataMapper.selectInfoRunTaskDataList(infoRunTaskData);
    }

    /**
     * 新增正在运行任务的业务数据
     *
     * @param infoRunTaskData 正在运行任务的业务数据
     * @return 结果
     */
    @Override
    public int insertInfoRunTaskData(InfoRunTaskData infoRunTaskData) {
        return infoRunTaskDataMapper.insertInfoRunTaskData(infoRunTaskData);
    }

    /**
     * 修改正在运行任务的业务数据
     *
     * @param infoRunTaskData 正在运行任务的业务数据
     * @return 结果
     */
    @Override
    @Transactional
    public int updateInfoRunTaskData(InfoRunTaskData infoRunTaskData) {
        if (Objects.isNull(infoRunTaskData.getBusinessKey()) || Objects.isNull(infoRunTaskData.getBusinessType())) {
            throw new RuntimeException("缺少必要参数");
        }
        InfoRunTaskData infoRunTaskData1 = infoRunTaskDataMapper.selectDetailInfo(infoRunTaskData.getBusinessKey(), infoRunTaskData.getBusinessType());
        BeanUtils.copyProperties(infoRunTaskData, infoRunTaskData1);
        return infoRunTaskDataMapper.updateInfoRunTaskData(infoRunTaskData1);
    }

    /**
     * 修改正在运行任务的业务数据(用于修改业务)
     * @param infoRunTaskData 正在运行任务的业务数据
     * @return
     */
    public int updateInfoRunTaskDatas(InfoRunTaskData infoRunTaskData) {
        if (Objects.isNull(infoRunTaskData.getBusinessKey()) || Objects.isNull(infoRunTaskData.getBusinessType())) {
            throw new RuntimeException("缺少必要参数");
        }
        InfoRunTaskData infoRunTaskData1 = infoRunTaskDataMapper.selectDetailInfo(infoRunTaskData.getBusinessKey(), infoRunTaskData.getBusinessType());
        BeanUtils.copyProperties(infoRunTaskData, infoRunTaskData1);
        return infoRunTaskDataMapper.updateInfoRunTaskDatas(infoRunTaskData1);
    }

    /**
     * 批量删除正在运行任务的业务数据
     *
     * @param ids 需要删除的正在运行任务的业务数据主键
     * @return 结果
     */
    @Override
    public int deleteInfoRunTaskDataByIds(Long[] ids) {
        return infoRunTaskDataMapper.deleteInfoRunTaskDataByIds(ids);
    }

    /**
     * 删除正在运行任务的业务数据信息
     *
     * @param id 正在运行任务的业务数据主键
     * @return 结果
     */
    @Override
    public int deleteInfoRunTaskDataById(Long id) {
        return infoRunTaskDataMapper.deleteInfoRunTaskDataById(id);
    }

    @Override
    public InfoRunTaskData selectDetailInfo(String businessKey, Integer businessType) {
        return infoRunTaskDataMapper.selectDetailInfo(businessKey, businessType);
    }

    @Override
    @Transactional
    public void updateStatus(String businessKey, Integer businessType, Integer status) {
        InfoRunTaskData infoRunTaskData = infoRunTaskDataMapper.selectDetailInfo(businessKey, businessType);
        infoRunTaskData.setProcessStatus(status);
        infoRunTaskDataMapper.updateInfoRunTaskData(infoRunTaskData);
    }

    @Override
    public void updateStatus(String businessKey, Integer businessType, Integer status, String processInstanceId) {
        InfoRunTaskData infoRunTaskData = infoRunTaskDataMapper.selectDetailInfo(businessKey, businessType);
        infoRunTaskData.setProcessStatus(status);
        infoRunTaskData.setProcessInstanceId(processInstanceId);
        if (status.compareTo(2) == 0) {
            //审核中的时候 修改申请时间
            infoRunTaskData.setApplyTime(new Date());
        }
        infoRunTaskDataMapper.updateInfoRunTaskData(infoRunTaskData);
    }

    @Override
    public void deleteData(String businessKey, Integer businessType) {
        infoRunTaskDataMapper.deleteData(businessKey, businessType);
    }
}
