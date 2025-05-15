package com.ruoyi.activiti.util;

import com.ruoyi.activiti.mapper.MyProcessMapper;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 流程相关工具类
 */

@Component
public class ActivitiUtil {

    @Resource
    private MyProcessMapper myProcessMapper;

    @Resource
    private RepositoryService repositoryService;

    public void backProcess(Task task) {
        String processInstanceId = task.getProcessInstanceId();
        // 取得所有历史任务按时间降序排序
        HistoryService historyService = SpringUtils.getBean(HistoryService.class);
        List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime()
                .desc()
                .list();
        if (ObjectUtils.isEmpty(htiList) || htiList.size() < 2) {
            return;
        }

        // list里的第二条代表上一个任务
        HistoricTaskInstance lastTask = htiList.get(1);
        // list里第二条代表当前任务
        HistoricTaskInstance curTask = htiList.get(0);
        // 当前节点的executionId
        String curExecutionId = curTask.getExecutionId();
        // 上个节点的taskId
        String lastTaskId = lastTask.getId();
        // 上个节点的executionId
        String lastExecutionId = lastTask.getExecutionId();
        if (null == lastTaskId) {
            throw new RuntimeException("上一任务不存在");
        }
        String processDefinitionId = lastTask.getProcessDefinitionId();
        RepositoryService repositoryService = SpringUtils.getBean(RepositoryService.class);
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        String lastActivityId = null;
        List<HistoricActivityInstance> haiFinishedList = historyService.createHistoricActivityInstanceQuery()
                .executionId(lastExecutionId).finished().list();
        for (HistoricActivityInstance hai : haiFinishedList) {
            if (lastTaskId.equals(hai.getTaskId())) {
                // 得到ActivityId，只有HistoricActivityInstance对象里才有此方法
                lastActivityId = hai.getActivityId();
                break;
            }
        }
        // 得到上个节点的信息
        FlowNode lastFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(lastActivityId);
        // 取得当前节点的信息
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        Execution execution = runtimeService.createExecutionQuery().executionId(curExecutionId).singleResult();
        String curActivityId = execution.getActivityId();
        FlowNode curFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(curActivityId);
        //记录当前节点的原活动方向
        List oriSequenceFlows = new ArrayList<>();
        oriSequenceFlows.addAll(curFlowNode.getOutgoingFlows());
        //清理活动方向
        curFlowNode.getOutgoingFlows().clear();
        //建立新方向
        List newSequenceFlowList = new ArrayList<>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(curFlowNode);
        newSequenceFlow.setTargetFlowElement(lastFlowNode);
        newSequenceFlowList.add(newSequenceFlow);
        curFlowNode.setOutgoingFlows(newSequenceFlowList);
        // 完成任务
        TaskService taskService = SpringUtils.getBean(TaskService.class);
        //获取任务处理人
        Map<String, Object> map = new HashMap<>();
        for (HistoricTaskInstance historicTaskInstance : htiList) {
            String taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
            if (lastActivityId.equals(taskDefinitionKey)) {
                String assignee = historicTaskInstance.getAssignee();
                map.put("assignee", assignee);
                break;
            }
        }
        taskService.complete(task.getId(), map, true);
        //恢复原方向
        curFlowNode.setOutgoingFlows(oriSequenceFlows);
        org.activiti.engine.task.Task nextTask = taskService
                .createTaskQuery().processInstanceId(processInstanceId).singleResult();
        // 设置执行人
        if (nextTask != null) {
            taskService.setAssignee(nextTask.getId(), lastTask.getAssignee());
        }
        //删除历史节点
        List<HistoricTaskInstance> newHtiList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime()
                .desc()
                .list();
        if (newHtiList.size() > 1) {
            for (int i = 1; i < newHtiList.size(); i++) {
                HistoricTaskInstance historicTaskInstance = newHtiList.get(i);
                if (historicTaskInstance.getTaskDefinitionKey().equals(lastActivityId)) {
                    //删除
                    System.out.println("===" + historicTaskInstance.getTaskDefinitionKey());
                    myProcessMapper.deleteHisTaskById(lastTask.getId());
                }
                if (historicTaskInstance.getTaskDefinitionKey().equals(task.getTaskDefinitionKey())) {
                    myProcessMapper.deleteHisTask(processInstanceId, task.getTaskDefinitionKey());
                }
            }
        }
    }

    public boolean isStartEvent(Task currentTask) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(currentTask.getProcessDefinitionId());
        List<Process> processes = bpmnModel.getProcesses();
        for (Process process : processes) {
            ArrayList<FlowElement> flowElements = new ArrayList<>(process.getFlowElements());
            for (int i = 0; i < flowElements.size(); i++) {
                FlowElement flowElement = flowElements.get(i);
                //usertask类型
                if (flowElement instanceof UserTask) {
                    UserTask userTask = (UserTask) flowElement;
                    if (userTask.getId().equals(currentTask.getTaskDefinitionKey())) {
                        List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();
                        for (SequenceFlow incomingFlow : incomingFlows) {
                            FlowElement sourceFlowElement1 = incomingFlow.getSourceFlowElement();
                            if (sourceFlowElement1 instanceof StartEvent) {
                                //是开始节点
                                return true;
                            }
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * 获取已经流转的线
     * @param bpmnModel                 流程图模板
     * @param historicActivityInstances 历史实例
     */
    public static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true);
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转： 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(targetFlowNode.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("highLightedFlowId", sequenceFlow.getId());
                            map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
                            tempMapList.add(map);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(tempMapList)) {
                    // 遍历匹配的集合，取得开始时间最早的一个
                    long earliestStamp = 0L;
                    String highLightedFlowId = null;
                    for (Map<String, Object> map : tempMapList) {
                        long highLightedFlowStartTime = Long.parseLong(map.get("highLightedFlowStartTime").toString());
                        if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
                            highLightedFlowId = map.get("highLightedFlowId").toString();
                            earliestStamp = highLightedFlowStartTime;
                        }
                    }

                    highLightedFlowIds.add(highLightedFlowId);
                }

            }

        }
        return highLightedFlowIds;
    }

    /**
     * 跳转到指定的任务
     * @param sourceTask 当前任务
     * @param targetTaskDefinitionKey 指定节点定义的id
     */
    public void jumpToTask(Task sourceTask,String targetTaskDefinitionKey) {

        String processInstanceId = sourceTask.getProcessInstanceId();
        // 取得所有历史任务按时间降序排序
        HistoryService historyService = SpringUtils.getBean(HistoryService.class);

        List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
               // .processDefinitionId(targetTaskDefinitionKey)
                .taskDefinitionKey(targetTaskDefinitionKey)
                .orderByTaskCreateTime()
                .desc()
                .list();
        HistoricTaskInstance lastTask = htiList.get(0);
        //取得当前节点的信息
        //取得指定节点的信息（TaskDefinitionKey）
        //保存当前节点的流向
        //新建流向，由当前节点指向新节点(指定节点)
        //当前节点完成任务=流行指定节点
        //将当前节点的流向还原
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        TaskService taskService = processEngine.getTaskService();
        String lastActivityId = lastTask.getTaskDefinitionKey();


        //获取该工作流的bpmn模板
        BpmnModel bpmnModel = repositoryService.getBpmnModel(sourceTask.getProcessDefinitionId());


        //  获取当前活动节点
        FlowNode currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sourceTask.getTaskDefinitionKey());
        //  获取指定活动节点
        FlowNode lastFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(targetTaskDefinitionKey);


        //  临时保存当前活动的原始方向 ，最后需要用到他恢复正确的方向
        List<SequenceFlow> originalSequenceFlowList = new ArrayList<>();
        originalSequenceFlowList.addAll(currentFlowNode.getOutgoingFlows());
        //  清理当前活动方向
        currentFlowNode.getOutgoingFlows().clear();


        //  建立新方向
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(currentFlowNode);//源
        newSequenceFlow.setTargetFlowElement(lastFlowNode);//目标
        List<SequenceFlow> newSequenceFlowList = new ArrayList<>();
        newSequenceFlowList.add(newSequenceFlow);
        //  当前节点指向新的方向
        currentFlowNode.setOutgoingFlows(newSequenceFlowList);

        //获取任务处理人
        Map<String, Object> map = new HashMap<>();
        for (HistoricTaskInstance historicTaskInstance : htiList) {
            String taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
            if (lastActivityId.equals(taskDefinitionKey)) {
                String assignee = historicTaskInstance.getAssignee();
                map.put("assignee", assignee);
                break;
            }
        }
        //  完成当前任务 = 跳转到指定的节点任务
        taskService.complete(sourceTask.getId(), map, true);

        //  恢复原始方向
        currentFlowNode.setOutgoingFlows(originalSequenceFlowList);

        org.activiti.engine.task.Task nextTask = taskService
                .createTaskQuery().processInstanceId(processInstanceId).singleResult();
        // 设置执行人
        if (nextTask != null) {
            taskService.setAssignee(nextTask.getId(), lastTask.getAssignee());
        }
        //删除历史节点
        List<HistoricTaskInstance> newHtiList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime()
                .desc()
                .list();

        if (newHtiList.size() > 1) {
            for (int i = 1; i < newHtiList.size(); i++) {
                HistoricTaskInstance historicTaskInstance = newHtiList.get(i);
                if (historicTaskInstance.getTaskDefinitionKey().equals(lastActivityId)) {
                    //删除
                    System.out.println("===" + historicTaskInstance.getTaskDefinitionKey());
                    myProcessMapper.deleteHisTaskById(lastTask.getId());
                }
                if (historicTaskInstance.getTaskDefinitionKey().equals(sourceTask.getTaskDefinitionKey())) {
                    myProcessMapper.deleteHisTask(processInstanceId, sourceTask.getTaskDefinitionKey());
                }
            }
        }
    }
}
