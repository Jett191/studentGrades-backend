package com.ruoyi.activiti.mapper;

import com.ruoyi.activiti.domain.DefineVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyProcessMapper {
    /**
     * 查询部署列表集合
     * @param defineVo
     * @return
     */
    List<DefineVo> listData(DefineVo defineVo);

    /**
     * 查询指定类型的流程定义key
     * @param processType
     * @return
     */
    String selectLastVersionProcessDefineKey(Integer processType);

    /**
     * 删除历史任务
     *
     * @param id
     */
    void deleteHisTaskById(@Param("id") String id);

    /**
     * 删除历史任务
     *
     * @param processInstanceId
     * @param taskDefKey
     */
    void deleteHisTask(@Param("processInstanceId") String processInstanceId,
                       @Param("taskDefKey") String taskDefKey);

}
