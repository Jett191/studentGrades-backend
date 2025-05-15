package com.ruoyi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 审核vo
 */
@Data
public class ApplyVo implements Serializable {
    private String processInstanceId;//流程实例ID
    private String comment;//审批意见
    private String taskId;//任务ID

    private String businessKey;//业务ID
    private Integer businessType;//业务类型

    private Map<String,Object> params;
}
