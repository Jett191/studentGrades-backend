package com.ruoyi.activiti.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 部署流程定义时的vo
 */
@Data
public class DefineProcessVo implements Serializable {
    private String type;
    private String stringBPMN;

}
