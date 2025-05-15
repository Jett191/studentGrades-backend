package com.ruoyi.infoRunTaskData.service;

import java.util.List;
import com.ruoyi.infoRunTaskData.domain.InfoRunTaskData;

/**
 * 正在运行任务的业务数据Service接口
 *
 * @author ruoyi
 * @date 2024-02-22
 */
public interface IInfoRunTaskDataService
{
    /**
     * 查询正在运行任务的业务数据
     *
     * @param id 正在运行任务的业务数据主键
     * @return 正在运行任务的业务数据
     */
    public InfoRunTaskData selectInfoRunTaskDataById(Long id);

    /**
     * 查询正在运行任务的业务数据列表
     *
     * @param infoRunTaskData 正在运行任务的业务数据
     * @return 正在运行任务的业务数据集合
     */
    public List<InfoRunTaskData> selectInfoRunTaskDataList(InfoRunTaskData infoRunTaskData);

    /**
     * 新增正在运行任务的业务数据
     *
     * @param infoRunTaskData 正在运行任务的业务数据
     * @return 结果
     */
    public int insertInfoRunTaskData(InfoRunTaskData infoRunTaskData);

    /**
     * 修改正在运行任务的业务数据
     *
     * @param infoRunTaskData 正在运行任务的业务数据
     * @return 结果
     */
    public int updateInfoRunTaskData(InfoRunTaskData infoRunTaskData);

    /**
     * 修改正在运行任务的业务数据(用于修改业务)
     *
     * @param infoRunTaskData 正在运行任务的业务数据
     * @return 结果
     */
    int updateInfoRunTaskDatas(InfoRunTaskData infoRunTaskData);

    /**
     * 批量删除正在运行任务的业务数据
     *
     * @param ids 需要删除的正在运行任务的业务数据主键集合
     * @return 结果
     */
    public int deleteInfoRunTaskDataByIds(Long[] ids);

    /**
     * 删除正在运行任务的业务数据信息
     *
     * @param id 正在运行任务的业务数据主键
     * @return 结果
     */
    public int deleteInfoRunTaskDataById(Long id);

    /**
     * 根据业务主键 和 类型查询详情
     * @param businessKey
     * @param businessType
     * @return
     */
    public InfoRunTaskData selectDetailInfo(String businessKey,Integer businessType);

    /**
     * 修改状态
     * @param businessKey 业务主键
     * @param businessType 业务类型
     * @param status 状态
     */
    public void  updateStatus(String businessKey,Integer businessType,Integer status);

    /**
     * 修改状态
     * @param businessKey 业务主键
     * @param businessType 业务类型
     * @param processInstanceId  流程示例id
     * @param status 状态
     */
    public void  updateStatus(String businessKey,Integer businessType,Integer status,String processInstanceId);


    /**
     * 删除数据
     * @param businessKey 业务主键
     * @param businessType 业务类型
     */
    void deleteData(String businessKey,Integer businessType);
}
