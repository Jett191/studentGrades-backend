package com.ruoyi.activiti.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.ruoyi.activiti.vo.ApplyVo;
import com.ruoyi.activiti.vo.HistoryVo;
import com.ruoyi.activiti.vo.MyTaskVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MyTaskService {


    /**
     * 发起流程
     *
     * @param businessKey  业务主键
     * @param businessType 业务类型
     * @return
     */
    int startTask(String businessKey, Integer businessType);

    /**
     * 撤销流程
     *
     * @param processInstanceId
     * @return
     */
    int revokeProcess(String processInstanceId, String businessKey, Integer businessType);

    /**
     * 查询我的代办任务
     *
     * @param myTaskVo
     * @return
     */
    List<MyTaskVo> selectMyTask(MyTaskVo myTaskVo);

    /**
     * 任务审核通过
     *
     * @param applyVo
     * @return
     */
    int passTask(ApplyVo applyVo);

    /**
     * 任务回退
     */
    int fallBackTask(ApplyVo applyVo);

    /**
     * 查询任务组数据
     * @param myTaskVo
     * @return
     */
    List<MyTaskVo> selectGroupTask(MyTaskVo myTaskVo);

    /**
     * 查询历史审批记录
     * @param processInstanceId
     * @return
     */
    List<HistoryVo> selectHistory(String processInstanceId);


    JSONArray selectBefore(ApplyVo applyVo);

    String fallBackTaskByTaskDefinitionKey(ApplyVo applyVo, String taskDefinitionKey);
}
