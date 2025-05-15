package com.ruoyi.activiti.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.activiti.domain.DefineProcessVo;
import com.ruoyi.activiti.domain.DefineVo;
import com.ruoyi.activiti.exception.CustomException;
import com.ruoyi.activiti.mapper.MyProcessMapper;
import com.ruoyi.activiti.util.ActivitiUtil;
import com.ruoyi.activiti.util.ProcessImageManager;
import com.ruoyi.activiti.vo.ActivitiVo;
import com.ruoyi.activiti.vo.HistoryVo;
import com.ruoyi.activiti.vo.ProcessHighlightEntity;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.ActivitiConstants;
import com.ruoyi.common.core.controller.BaseController;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;

import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.ISysUserService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.ProcessInstanceHistoryLog;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 汇讯数码科技(深圳)有限公司
 * 创建日期:2020/10/22-15:16
 * 版本   开发者     日期
 * 1.0    Danny    2020/10/22
 */
@RestController
@RequestMapping("/processDefinition")
public class ProcessDefinitionController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

    @Resource
    private MyProcessMapper myProcessMapper;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ISysUserService userService;

    /**
     * 获取流程定义集合
     *
     * @param
     * @return
     */
    @GetMapping(value = "/list")
    public TableDataInfo list(DefineVo defineVo) {
        startPage();
        List<DefineVo> defineVos = myProcessMapper.listData(defineVo);
        return getDataTable(defineVos);
    }

    /**
     * 通过stringBPMN添加流程定义
     *
     * @param processVo
     * @return
     */
    @PostMapping(value = "/myAddDeploymentByString")
    public AjaxResult myAddDeploymentByString(@RequestBody DefineProcessVo processVo, HttpServletRequest request) {

        String stringBPMN = processVo.getStringBPMN();
        //类型
        String type = processVo.getType();

        Deployment deploy = repositoryService.createDeployment()
                .addString("CreateWithBPMNJS.bpmn", stringBPMN)
                .category(type)
                .deploy();
        return AjaxResult.success();

    }

    /**
     * 获取流程定义XML
     *
     * @param response
     * @param deploymentId
     * @param resourceName
     */
    @GetMapping(value = "/getDefinitionXML")
    public void getProcessDefineXML(HttpServletResponse response,
                                    @RequestParam("deploymentId") String deploymentId,
                                    @RequestParam("resourceName") String resourceName) throws IOException {

        InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
        int count = inputStream.available();
        byte[] bytes = new byte[count];
        response.setContentType("text/xml");
        OutputStream outputStream = response.getOutputStream();
        while (inputStream.read(bytes) != -1) {
            outputStream.write(bytes);
        }
        inputStream.close();
    }


    /**
     * 删除流程定义
     *
     * @param deploymentId
     * @return
     */
    @Log(title = "流程定义管理", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/remove/{deploymentId}/{category}")
    public AjaxResult delDefinition(@PathVariable("deploymentId") String deploymentId,
                                    @PathVariable("category") Integer category) {
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(deploymentId).singleResult();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId(deployment.getTenantId())
                .processDefinitionCategory(String.valueOf(category)).list();

        //删除流程定义
        repositoryService.deleteDeployment(deploymentId, true);

        return AjaxResult.success();
    }

    @Autowired
    ProcessImageManager processImageManager;

    @RequestMapping(value = "/findPicture", method = RequestMethod.GET)
    public void findPicture(@RequestParam(value = "processInstanceId") String processInstanceId,
                            HttpServletResponse response) throws Exception {

        InputStream imageStream = processImageManager.getFlowImgByProcInstId(processInstanceId);
        try {
            // 输出资源内容到相应对象
            byte[] b = new byte[1024];
            int len;
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (imageStream != null) {
                try {
                    imageStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public BpmnModel getBpmnModel(String procDefId) throws CustomException {
        try {
            return repositoryService.getBpmnModel(procDefId);
        } catch (ActivitiObjectNotFoundException e) {
            throw new CustomException("流程定义数据不存在");
        }
    }
    public ProcessDefinition getProcessDefinition(String procDefId, String instanceId) throws CustomException {
        if (StrUtil.isBlank(procDefId)) {
            if (StrUtil.isBlank(instanceId)) {
                throw new CustomException("流程实例id，流程定义id 两者不能都为空");
            }
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(instanceId)
                    .singleResult();
            if (processInstance == null) {
                HistoricProcessInstance histProcInst = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(instanceId)
                        .singleResult();
                if (histProcInst == null) {
                    throw new CustomException("查询失败，请检查传入的 instanceId 是否正确");
                }
                procDefId = histProcInst.getProcessDefinitionId();
            } else {
                procDefId = processInstance.getProcessDefinitionId();
            }
        }

        try {
            return repositoryService.getProcessDefinition(procDefId);
        } catch (ActivitiObjectNotFoundException e) {
            throw new CustomException("该流程属于之前流程，已删除");
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/getHighLightedNodeVoByProcessInstanceId")
    public AjaxResult getActivitiProcessHighlight(String instanceId, String procDefId) throws CustomException {
        ProcessDefinition processDefinition = getProcessDefinition(procDefId, instanceId);

        procDefId = processDefinition.getId();
        BpmnModel bpmnModel = getBpmnModel(procDefId);

        List<HistoricActivityInstance> histActInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId).orderByHistoricActivityInstanceId().asc().list();

        ProcessHighlightEntity highlightEntity = getHighLightedData(bpmnModel.getMainProcess(), histActInstances);
        highlightEntity.setModelName(processDefinition.getName());
        // Map缓存，提高获取流程文件速度
        if (ActivitiConstants.BPMN_XML_MAP.containsKey(procDefId)) {
            highlightEntity.setModelXml(ActivitiConstants.BPMN_XML_MAP.get(procDefId));
        } else {
            InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
            try (Reader reader = new InputStreamReader(bpmnStream, StandardCharsets.UTF_8)) {
                String xmlString = IoUtil.read(reader);
                highlightEntity.setModelXml(xmlString);
                ActivitiConstants.BPMN_XML_MAP.put(procDefId, xmlString);
            } catch (IOException e) {
                System.err.println("[获取流程数据] 失败."+ e.getMessage());
               // throw new CustomException("获取流程数据失败，请稍后重试");
                return error("获取流程数据失败，请稍后重试");
            }
        }

        return success(highlightEntity);
    }

    private ProcessHighlightEntity getHighLightedData(Process process,
                                                      List<HistoricActivityInstance> historicActInstances) {
        ProcessHighlightEntity entity = new ProcessHighlightEntity();
        // 已执行的节点id
        Set<String> executedActivityIds = new HashSet<>();
        // 正在执行的节点id
        Set<String> activeActivityIds = new HashSet<>();
        // 高亮流程已发生流转的线id集合
        Set<String> highLightedFlowIds = new HashSet<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActInstances) {
            FlowNode flowNode = (FlowNode) process.getFlowElement(historicActivityInstance.getActivityId(), true);
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
                executedActivityIds.add(historicActivityInstance.getActivityId());
            } else {
                activeActivityIds.add(historicActivityInstance.getActivityId());
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) process.getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转：
             * 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
             * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) process.getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(sequenceFlow.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActInstances) {
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

        entity.setActiveActivityIds(activeActivityIds);
        entity.setExecutedActivityIds(executedActivityIds);
        entity.setHighlightedFlowIds(highLightedFlowIds);

        return entity;
    }
    @RequestMapping(value = "/getOneActivityVoByProcessInstanceIdAndActivityId", method = RequestMethod.GET)
    public AjaxResult selectHistory(@RequestParam("procInstId") String processInstanceId,
            @RequestParam("elementId") String elementId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        SysUser user = null;
        HistoricActivityInstance historicActivityInstances = null;
        if (historicProcessInstance != null) {
            // 获取当前活动的ActivityId
//            String currentActivityId = historyService.createHistoricActivityInstanceQuery()
//                    .processInstanceId(processInstanceId)
//                    .activityType("userTask") // 可以指定活动类型，例如用户任务
//                    .unfinished() // 查询当前正在执行的活动
//                    .singleResult()
//                    .getActivityId();

            System.out.println("当前活动的ActivityId: " + elementId);

            // 查询所有历史活动实例
            historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .activityId(elementId)
                    .singleResult();

            if(historicActivityInstances.getAssignee()!=null){
                user = userService.selectUserById(Long.parseLong(historicActivityInstances.getAssignee()));
            }
            ProcessInstanceHistoryLog pi = historyService.createProcessInstanceHistoryLogQuery(processInstanceId).singleResult();
        } else {
            System.out.println("流程实例未找到或已结束");
        }
        ActivitiVo activitiVo = new ActivitiVo();
        if(user!=null){     //当前节点没审批人
            activitiVo.setActivityName(historicActivityInstances.getActivityName());
            activitiVo.setApprover(user.getUserName());
            activitiVo.setEndTime(historicActivityInstances.getEndTime().toString());
        }

        return success(activitiVo);
    }

    @RequestMapping(value = "/findNodeInfo", method = RequestMethod.GET)
    public AjaxResult findNodeInfo(@RequestParam(value = "processInstanceId") String processInstanceId,@RequestParam(value = "activityId") String activityId) throws Exception {

        return AjaxResult.success(processImageManager.getActivityInfo(activityId,processInstanceId));

    }


}
