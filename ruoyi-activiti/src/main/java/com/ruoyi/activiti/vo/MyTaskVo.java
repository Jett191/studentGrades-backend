package com.ruoyi.activiti.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的任务
 */
@Data
public class MyTaskVo implements Serializable {

    private String taskId;
    private String taskName;
    private String processInstanceId;
    private String businessTitle;
    private String businessType;
    private String businessKey;
    private String applyUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTaskTime;
    /** 流程状态 默认1 */
    private Integer processStatus;

    //当前审批人
    private String assignee;

    //拾取时间
    private Date claimTime;

}
