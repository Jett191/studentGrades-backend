package com.ruoyi.activiti.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.ruoyi.activiti.service.MyTaskService;
import com.ruoyi.activiti.util.ActivitiUtil;
import com.ruoyi.activiti.vo.ApplyVo;
import com.ruoyi.activiti.vo.HistoryVo;
import com.ruoyi.activiti.vo.MyTaskVo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private MyTaskService myTaskService;


    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private ActivitiUtil activitiUtil;

    /**
     * 发起流程
     *
     * @param businessKey  业务主键
     * @param businessType 业务类型
     * @return
     */
    @RequestMapping(value = "/startTask", method = RequestMethod.GET)
    public AjaxResult startTask(@RequestParam("businessKey") String businessKey, @RequestParam("businessType") Integer businessType) {
        int i = myTaskService.startTask(businessKey, businessType);
        return toAjax(i);
    }

    /**
     * 撤销流程
     *
     * @param processInstanceId 流程示例id
     * @return
     */
    @RequestMapping(value = "/revokeProcess", method = RequestMethod.GET)
    public AjaxResult revokeProcess(@RequestParam("processInstanceId") String processInstanceId, @RequestParam("businessKey") String businessKey, @RequestParam("businessType") Integer businessType) {
        int i = myTaskService.revokeProcess(processInstanceId, businessKey, businessType);
        return toAjax(i);
    }


    /**
     * 查询我的代办任务
     *
     * @param myTaskVo
     * @return
     */
    @RequestMapping(value = "/selectMyTask", method = RequestMethod.GET)
    public TableDataInfo selectMyTask(MyTaskVo myTaskVo) {
        myTaskVo.setAssignee(String.valueOf(getUserId()));
        startPage();
        List<MyTaskVo> myTaskVos = myTaskService.selectMyTask(myTaskVo);
        return getDataTable(myTaskVos);
    }


    /**
     * 查询任务组数据
     *
     * @param myTaskVo
     * @return
     */
    @RequestMapping(value = "/selectGroupTask", method = RequestMethod.GET)
    public TableDataInfo selectGroupTask(MyTaskVo myTaskVo) {
        myTaskVo.setAssignee(String.valueOf(getUserId()));
        startPage();
        List<MyTaskVo> myTaskVos = myTaskService.selectGroupTask(myTaskVo);
        return getDataTable(myTaskVos);
    }

    /**
     * 审核通过任务
     *
     * @param applyVo
     * @return
     */
    @RequestMapping(value = "/passTask", method = RequestMethod.POST)
    public AjaxResult passTask(@RequestBody ApplyVo applyVo) {
        int i = myTaskService.passTask(applyVo);
        return AjaxResult.success(i);
    }

    /**
     * 任务回退
     *
     * @param applyVo
     * @return
     */
    @RequestMapping(value = "/fallBackTask", method = RequestMethod.POST)
    public AjaxResult fallBackTask(@RequestBody ApplyVo applyVo) {
        int i = myTaskService.fallBackTask(applyVo);
        return toAjax(i);
    }

    /**
     * 任务拾取
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/claimTask", method = RequestMethod.GET)
    public AjaxResult claimTask(@RequestParam("taskId") String taskId) {
        taskService.claim(taskId, getUserId() + "");
        return AjaxResult.success();
    }

    /**
     * 归还任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/unClaimTask", method = RequestMethod.GET)
    public AjaxResult unClaimTask(@RequestParam("taskId") String taskId) {
        taskService.unclaim(taskId);
        return AjaxResult.success();
    }


    /**
     * 查询审批历史记录
     *
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/selectHistory", method = RequestMethod.GET)
    public AjaxResult selectHistory(@RequestParam("processInstanceId") String processInstanceId) {
        List<HistoryVo> historyVos = myTaskService.selectHistory(processInstanceId);
        return AjaxResult.success(historyVos);
    }
    /**
     * 查询可回退的任务节点
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/selectBefore", method = RequestMethod.GET)
    public AjaxResult selectBefore(ApplyVo applyVo) {
        JSONArray result = myTaskService.selectBefore(applyVo);
        return AjaxResult.success(result);
    }

    /**
     * 查询可回退的任务节点
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/fallBackTaskByTaskDefinitionKey", method = RequestMethod.GET)
    public AjaxResult fallBackTaskByTaskDefinitionKey(ApplyVo applyVo, String taskDefinitionKey) {
        String result = myTaskService.fallBackTaskByTaskDefinitionKey(applyVo,taskDefinitionKey);
        return AjaxResult.success(result);
    }

}
