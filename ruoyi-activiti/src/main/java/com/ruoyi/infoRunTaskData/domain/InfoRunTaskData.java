package com.ruoyi.infoRunTaskData.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 正在运行任务的业务数据对象 info_run_task_data
 *
 * @author ruoyi
 * @date 2024-02-22
 */
public class InfoRunTaskData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 业务businessId */
    @Excel(name = "业务businessId")
    private String businessKey;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private Integer businessType;

    /** 业务标题 */
    @Excel(name = "业务标题")
    private String businessTitle;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String processInstanceId;

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    /** 流程状态 默认1 */
    @Excel(name = "流程状态 默认1")
    private Integer processStatus;

    private String applyUser;//申请人

    private Date applyTime;//发起申请时间

    private String userIds;

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setBusinessKey(String businessKey)
    {
        this.businessKey = businessKey;
    }

    public String getBusinessKey()
    {
        return businessKey;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public void setBusinessTitle(String businessTitle)
    {
        this.businessTitle = businessTitle;
    }

    public String getBusinessTitle()
    {
        return businessTitle;
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
        return "InfoRunTaskData{" +
                "id=" + id +
                ", businessKey=" + businessKey +
                ", businessType=" + businessType +
                ", businessTitle='" + businessTitle + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", processStatus=" + processStatus +
                ", applyUser='" + applyUser + '\'' +
                ", applyTime=" + applyTime +
                ", userIds=" + userIds +
                '}';
    }
}
