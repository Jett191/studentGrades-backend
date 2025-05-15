// Todo XSJ
package com.ruoyi.xscj.kcap.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程安排对象 xscj_kcap
 *
 */
public class Kcap extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程安排ID */
    private String kcapId;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String kcName;

    /** 课程开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "课程开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date kcQtime;

    /** 课程结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "课程结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date kcZtime;

    /** 课程地点 */
    @Excel(name = "课程地点")
    private String kcDd;

    /** 授课教师 */
    @Excel(name = "授课教师")
    private String skJs;

    /** 用户ID */
    private Integer userId;

    /** 部门ID */
    private Integer deptId;

    public void setKcapId(String kcapId)
    {
        this.kcapId = kcapId;
    }

    public String getKcapId()
    {
        return kcapId;
    }
    public void setKcName(String kcName)
    {
        this.kcName = kcName;
    }

    public String getKcName()
    {
        return kcName;
    }
    public void setKcQtime(Date kcQtime)
    {
        this.kcQtime = kcQtime;
    }

    public Date getKcQtime()
    {
        return kcQtime;
    }
    public void setKcZtime(Date kcZtime)
    {
        this.kcZtime = kcZtime;
    }

    public Date getKcZtime()
    {
        return kcZtime;
    }
    public void setKcDd(String kcDd)
    {
        this.kcDd = kcDd;
    }

    public String getKcDd()
    {
        return kcDd;
    }
    public void setSkJs(String skJs)
    {
        this.skJs = skJs;
    }

    public String getSkJs()
    {
        return skJs;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }
    public void setDeptId(Integer deptId)
    {
        this.deptId = deptId;
    }

    public Integer getDeptId()
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("kcapId", getKcapId())
                .append("kcName", getKcName())
                .append("kcQtime", getKcQtime())
                .append("kcZtime", getKcZtime())
                .append("kcDd", getKcDd())
                .append("skJs", getSkJs())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .toString();
    }
}
