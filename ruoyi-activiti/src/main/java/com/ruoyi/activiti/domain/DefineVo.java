package com.ruoyi.activiti.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DefineVo implements Serializable {
    private String deploymentId;
    private String processDefinitionId;
    private String processDefinitionName;
    private String processDefinitionKey;
    private String processDefinitionVersion;
    private String processDefinitionType;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deployTime;
    private String resourceName;
}
