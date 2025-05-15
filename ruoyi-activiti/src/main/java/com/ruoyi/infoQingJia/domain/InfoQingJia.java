package com.ruoyi.infoQingJia.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 请假对象 info_qing_jia
 *
 * @author ruoyi
 * @date 2024-02-22
 */
public class InfoQingJia extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 请假标题
     */
    @Excel(name = "请假标题")
    private String qingJiaTitle;

    /**
     * 请假原因
     */
    @Excel(name = "请假原因")
    private String qingJiaReason;

    /**
     * 请假天数
     */
    @Excel(name = "请假天数")
    private Long qingJiaDay;

    /**
     * 请假人
     */
    @Excel(name = "请假人")
    private Long userId;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 状态
     */
    private Integer processStatus;

    /**
     * 流程示例id
     */
    private String processInstanceId;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setQingJiaTitle(String qingJiaTitle) {
        this.qingJiaTitle = qingJiaTitle;
    }

    public String getQingJiaTitle() {
        return qingJiaTitle;
    }

    public void setQingJiaReason(String qingJiaReason) {
        this.qingJiaReason = qingJiaReason;
    }

    public String getQingJiaReason() {
        return qingJiaReason;
    }

    public void setQingJiaDay(Long qingJiaDay) {
        this.qingJiaDay = qingJiaDay;
    }

    public Long getQingJiaDay() {
        return qingJiaDay;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("qingJiaTitle", getQingJiaTitle())
                .append("qingJiaReason", getQingJiaReason())
                .append("qingJiaDay", getQingJiaDay())
                .append("userId", getUserId())
                .toString();
    }
}
