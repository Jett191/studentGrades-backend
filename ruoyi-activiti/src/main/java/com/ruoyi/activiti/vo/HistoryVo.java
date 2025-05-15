package com.ruoyi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HistoryVo implements Serializable {
    private String taskName;
    private String COMMENT;
    private Date startTime;
    private Date endTime;
    private String nickName;
}
