package com.ruoyi.infoRunTaskData.mapper;

import java.util.List;
import com.ruoyi.infoRunTaskData.domain.InfoRunTaskData;
import org.apache.ibatis.annotations.Param;

/**
 * 正在运行任务的业务数据Mapper接口
 *
 * @author ruoyi
 * @date 2024-02-22
 */
public interface InfoRunTaskDataMapper
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
     * @param infoRunTaskData
     * @return
     */
    public int updateInfoRunTaskDatas(InfoRunTaskData infoRunTaskData);

    /**
     * 删除正在运行任务的业务数据
     *
     * @param id 正在运行任务的业务数据主键
     * @return 结果
     */
    public int deleteInfoRunTaskDataById(Long id);

    /**
     * 批量删除正在运行任务的业务数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInfoRunTaskDataByIds(Long[] ids);


    /**
     * 根据业务主键 和 类型查询详情
     * @param businessKey
     * @param businessType
     * @return
     */
    public InfoRunTaskData selectDetailInfo(@Param("businessKey") String businessKey,
                                            @Param("businessType") Integer businessType);


    /**
     * 删除数据
     * @param businessKey 业务主键
     * @param businessType 业务类型
     */
    void deleteData(@Param("businessKey") String businessKey,
                    @Param("businessType") Integer businessType);
}
