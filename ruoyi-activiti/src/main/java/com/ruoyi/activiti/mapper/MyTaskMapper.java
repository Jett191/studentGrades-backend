package com.ruoyi.activiti.mapper;

import com.ruoyi.activiti.vo.HistoryVo;
import com.ruoyi.activiti.vo.MyTaskVo;

import java.util.List;

public interface MyTaskMapper {
    /**
     * 查询我的代办任务
     * @param myTaskVo
     * @return
     */
    List<MyTaskVo> selectMyTask(MyTaskVo myTaskVo);

    /**
     * 查询任务组数据
     * @param myTaskVo
     * @return
     */
    List<MyTaskVo> selectGroupTask(MyTaskVo myTaskVo);


    /**
     * 查询历史记录
     * @param processInstanceId
     * @return
     */
    List<HistoryVo> selectHistory(String processInstanceId);
}
