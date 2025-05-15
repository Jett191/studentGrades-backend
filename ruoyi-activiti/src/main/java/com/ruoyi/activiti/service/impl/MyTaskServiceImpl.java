package com.ruoyi.activiti.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.ruoyi.activiti.mapper.MyProcessMapper;
import com.ruoyi.activiti.mapper.MyTaskMapper;
import com.ruoyi.activiti.service.MyTaskService;
import com.ruoyi.activiti.util.ActivitiUtil;
import com.ruoyi.activiti.vo.ApplyVo;
import com.ruoyi.activiti.vo.HistoryVo;
import com.ruoyi.activiti.vo.MyTaskVo;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.infoHiComment.domain.InfoHiComment;
import com.ruoyi.infoHiComment.mapper.InfoHiCommentMapper;
import com.ruoyi.infoRunTaskData.domain.InfoRunTaskData;
import com.ruoyi.infoRunTaskData.mapper.InfoRunTaskDataMapper;
import com.ruoyi.infoRunTaskData.service.IInfoRunTaskDataService;
import org.activiti.api.process.model.events.BPMNSequenceFlowTakenEvent;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MyTaskServiceImpl implements MyTaskService {

    @Resource
    private RuntimeService runtimeService;


    @Resource
    private MyProcessMapper myProcessMapper;

    @Resource
    private IInfoRunTaskDataService infoRunTaskDataService;

    @Resource
    private MyTaskMapper myTaskMapper;

    @Resource
    private TaskService taskService;
    @Resource
    private ActivitiUtil activitiUtil;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private InfoHiCommentMapper infoHiCommentMapper;

    @Resource
    private InfoRunTaskDataMapper infoRunTaskDataMapper;

    @Transactional
    @Override
    public int startTask(String businessKey, Integer businessType) {
        //1.查询最新部署的一条流程
        String processDefineKey = myProcessMapper.selectLastVersionProcessDefineKey(businessType);
        if (StringUtils.isEmpty(processDefineKey)) {
            throw new RuntimeException("未找到相应流程，请联系管理员");
        }



//        //获取runtimeService
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
        //启动流程

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefineKey,processDefineKey);
        Task currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        //taskService.setAssignee(currentTask.getId(), "1,2,100");

        //获取候选人
        String targetUserTaskId= currentTask.getId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(currentTask.getProcessDefinitionId());
        List<Process> processes = bpmnModel.getProcesses();
        for (Process process : processes) {
            List<UserTask> userTaskList = process.findFlowElementsOfType(UserTask.class);
            for (UserTask userTask : userTaskList) {
                //if(userTask.getId().equals(targetUserTaskId)){
                    userTask.getCandidateGroups();

                    List<String> candidateUsers = userTask.getCandidateUsers();
                    if (!CollectionUtils.isEmpty(candidateUsers)) {
                       // userTask.setAssignee("1");
                    }
                           /*String[] assingneeArray=complateDto.getUserIds().split(",");
                           variables.put("assigneeList", Arrays.asList(assingneeArray));*/
                    System.out.println("candidateUsers:"+candidateUsers+"; group:"+userTask.getCandidateGroups().toString());
                //}
            }
        }
        //修改业务数据  //修改成审核中
        infoRunTaskDataService.updateStatus(businessKey, businessType, 2, processInstance.getProcessInstanceId());
        //新增历史记录
        InfoHiComment infoHiComment = new InfoHiComment();
        infoHiComment.setCreateTime(new Date());
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        infoHiComment.setTaskName(loginUser.getUser().getNickName() + "-提交审批");
        infoHiComment.setCreateName(SecurityUtils.getLoginUser().getUser().getNickName());
        infoHiComment.setMessage("发起申请");
        infoHiComment.setProcessInstanceId(processInstance.getProcessInstanceId());
        infoHiCommentMapper.insertInfoHiComment(infoHiComment);
        return 1;
    }


    @Transactional
    @Override
    public int revokeProcess(String processInstanceId, String businessKey, Integer businessType) {
        runtimeService.deleteProcessInstance(processInstanceId, "主动撤销流程");
        //修改业务状态 为未提交
        infoRunTaskDataService.updateStatus(businessKey, businessType, 1);
        return 1;
    }

    @Override
    public List<MyTaskVo> selectMyTask(MyTaskVo myTaskVo) {
        List<MyTaskVo> myTaskVos = myTaskMapper.selectMyTask(myTaskVo);
        return myTaskVos;
    }


    @Transactional
    @Override
    public int passTask(ApplyVo applyVo) {
        Map<String, Object> variables = new HashMap<>();
        if (!Objects.isNull(applyVo.getParams())) {
            variables.putAll(applyVo.getParams());
        }


        Task currentTask = taskService.createTaskQuery().taskId(applyVo.getTaskId()).singleResult();

        taskService.addComment(applyVo.getTaskId(), applyVo.getProcessInstanceId(), applyVo.getComment());
        taskService.complete(applyVo.getTaskId(), variables);
        //查询这个任务是否还存在
        List<Task> list = taskService.createTaskQuery().processInstanceId(applyVo.getProcessInstanceId()).list();

        //新增历史记录
        if (!list.isEmpty()) {
            //新增历史记录
            InfoHiComment infoHiComment = new InfoHiComment();
            infoHiComment.setCreateTime(new Date());
            infoHiComment.setCreateName(SecurityUtils.getLoginUser().getUser().getNickName());
            infoHiComment.setMessage(applyVo.getComment());
            infoHiComment.setTaskName(currentTask.getName());
            infoHiComment.setProcessInstanceId(applyVo.getProcessInstanceId());
            infoHiCommentMapper.insertInfoHiComment(infoHiComment);
        } else {
            //说明所有的任务已经完成 修改业务数据状态为完成 3审核通过
            infoRunTaskDataService.updateStatus(applyVo.getBusinessKey(), applyVo.getBusinessType(), 3);
            InfoHiComment infoHiComment = new InfoHiComment();
            infoHiComment.setCreateTime(new Date());
            infoHiComment.setCreateName(SecurityUtils.getLoginUser().getUser().getNickName());
            infoHiComment.setMessage(applyVo.getComment());
            infoHiComment.setTaskName("审批流程通过");
            infoHiComment.setProcessInstanceId(applyVo.getProcessInstanceId());
            infoHiCommentMapper.insertInfoHiComment(infoHiComment);
        }
        return 1;
    }


    @Transactional
    @Override
    public int fallBackTask(ApplyVo applyVo) {
        taskService.addComment(applyVo.getTaskId(), applyVo.getProcessInstanceId(), applyVo.getComment());
        // 获取当前任务信息
        Task currentTask = taskService.createTaskQuery().taskId(applyVo.getTaskId()).singleResult();

        if (Objects.isNull(currentTask)) {
            throw new RuntimeException("当前任务不存在");
        }

        //判断是不是开始节点
        boolean startEvent = activitiUtil.isStartEvent(currentTask);
        if (startEvent) {
            //说明已经退回到开始节点 修改业务数据状态为完成 4审核不通过
            infoRunTaskDataService.updateStatus(applyVo.getBusinessKey(), applyVo.getBusinessType(), 4);
            //删除流程
            runtimeService.deleteProcessInstance(currentTask.getProcessInstanceId(), "退回到开始节点");
        } else {
            //退回任务
            activitiUtil.backProcess(currentTask);
        }

        //新增历史记录
        InfoHiComment infoHiComment = new InfoHiComment();
        infoHiComment.setCreateTime(new Date());
        infoHiComment.setCreateName(SecurityUtils.getLoginUser().getUser().getNickName());
        infoHiComment.setMessage(applyVo.getComment());
        infoHiComment.setProcessInstanceId(applyVo.getProcessInstanceId());
        infoHiComment.setTaskName(currentTask.getName() + "-退回");
        infoHiCommentMapper.insertInfoHiComment(infoHiComment);


        return 1;
    }

    @Override
    public List<MyTaskVo> selectGroupTask(MyTaskVo myTaskVo) {
        return myTaskMapper.selectGroupTask(myTaskVo);
    }

    @Override
    public List<HistoryVo> selectHistory(String processInstanceId) {
        return myTaskMapper.selectHistory(processInstanceId);
    }
    @Override
    public JSONArray selectBefore(ApplyVo applyVo) {
        JSONArray beforeList = new JSONArray();
        // 获取当前任务信息
        Task currentTask = taskService.createTaskQuery().taskId(applyVo.getTaskId()).singleResult();

        boolean startEvent = activitiUtil.isStartEvent(currentTask);
        JSONObject startObj = new JSONObject();
        startObj.putOnce("dkey", "0");
        startObj.putOnce("taskName", "不予通过");
        if (startEvent) {
            beforeList.add(startObj);
            return beforeList;
        }
        String processInstanceId = currentTask.getProcessInstanceId();
        // 取得所有历史任务按时间降序排序
        HistoryService historyService = SpringUtils.getBean(HistoryService.class);
        List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime()
                .asc()
                .list();
        if (ObjectUtils.isEmpty(htiList) || htiList.size() < 2) {
            beforeList.add(startObj);
            return beforeList;
        }

        // list里的第二条代表上一个任务
//        HistoricTaskInstance lastTask = htiList.get(1);
//        // list里第二条代表当前任务
//        HistoricTaskInstance curTask = htiList.get(0);
//        // 当前节点的executionId
//        String curExecutionId = curTask.getExecutionId();
//        // 上个节点的taskId
//        String lastTaskId = lastTask.getId();
//        // 上个节点的executionId
//        String lastExecutionId = lastTask.getExecutionId();
//        if (null == lastTaskId) {
//            throw new RuntimeException("上一任务不存在");
//        }
        for (int i = 0; i < htiList.size() - 1; i++) {
            HistoricTaskInstance historicTaskInstance = htiList.get(i);

            JSONObject jo = new JSONObject();
            jo.putOnce("dkey", historicTaskInstance.getTaskDefinitionKey());
            jo.putOnce("taskName", historicTaskInstance.getName());
            beforeList.add(jo);
        }
//        for(HistoricTaskInstance historicTaskInstance : htiList){
//            JSONObject jo = new JSONObject();
//            jo.append("taskId", historicTaskInstance.getId());
//            jo.append("taskName", historicTaskInstance.getName());
//            beforeList.add(jo);
//        }
        beforeList.add(startObj);
        System.out.println(beforeList.toString());
        return beforeList;
    }

    @Override
    public String fallBackTaskByTaskDefinitionKey(ApplyVo applyVo, String taskDefinitionKey) {
        // 获取当前任务信息
        Task currentTask = taskService.createTaskQuery().taskId(applyVo.getTaskId()).singleResult();
        if(taskDefinitionKey.equals("0")){
            //说明选择退回到开始节点 修改业务数据状态为完成 4审核不通过
            infoRunTaskDataService.updateStatus(applyVo.getBusinessKey(), applyVo.getBusinessType(), 4);
            //删除流程
            runtimeService.deleteProcessInstance(currentTask.getProcessInstanceId(), "退回到开始节点");
        }else if (StringUtils.isNotEmpty(applyVo.getTaskId())) {
            Task task = taskService.createTaskQuery().taskId(applyVo.getTaskId()).singleResult();
            activitiUtil.jumpToTask(task, taskDefinitionKey);
        }
        //新增历史记录
        InfoHiComment infoHiComment = new InfoHiComment();
        infoHiComment.setCreateTime(new Date());
        infoHiComment.setCreateName(SecurityUtils.getLoginUser().getUser().getNickName());
        infoHiComment.setMessage(applyVo.getComment());
        infoHiComment.setProcessInstanceId(applyVo.getProcessInstanceId());
        infoHiComment.setTaskName(currentTask.getName() + "-退回");
        infoHiCommentMapper.insertInfoHiComment(infoHiComment);
        return "success";
    }


}
