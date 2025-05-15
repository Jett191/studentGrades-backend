package com.ruoyi.infoHiComment.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 审批记录对象 info_hi_comment
 * 
 * @author ruoyi
 * @date 2024-03-11
 */
public class InfoHiComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 操作日志 */
    @Excel(name = "操作日志")
    private String message;

    /** 操作人 */
    @Excel(name = "操作人")
    private String createName;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String processInstanceId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }
    public void setCreateName(String createName) 
    {
        this.createName = createName;
    }

    public String getCreateName() 
    {
        return createName;
    }
    public void setProcessInstanceId(String processInstanceId) 
    {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() 
    {
        return processInstanceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("message", getMessage())
            .append("createTime", getCreateTime())
            .append("createName", getCreateName())
            .append("processInstanceId", getProcessInstanceId())
            .toString();
    }
}
