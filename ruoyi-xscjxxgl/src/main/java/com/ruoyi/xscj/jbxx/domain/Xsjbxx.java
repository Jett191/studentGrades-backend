// Todo PJX
// Todo WJS
package com.ruoyi.xscj.jbxx.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生基本信息对象 xscj_xsjbxx
 * @author LukAsy_
 * @date 2025/4/19
 */
public class Xsjbxx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学生基本信息ID */
    private String xsjbxxId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String xm;

    /** 学号 */
    @Excel(name = "学号")
    private String xh;

    /** 性别 */
    @Excel(name = "性别")
    private String xb;

    /** 班级 */
    @Excel(name = "班级")
    private String bj;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String yx;

    /** 学籍状态 */
    @Excel(name = "学籍状态")
    private String xjZt;

    /** 用户ID */
    private Integer userId;

    /** 部门ID */
    private Integer deptId;

    /** 所属用户ID */
    private Integer ssUserId;

    public void setXsjbxxId(String xsjbxxId)
    {
        this.xsjbxxId = xsjbxxId;
    }

    public String getXsjbxxId()
    {
        return xsjbxxId;
    }
    public void setXm(String xm)
    {
        this.xm = xm;
    }

    public String getXm()
    {
        return xm;
    }
    public void setXh(String xh)
    {
        this.xh = xh;
    }

    public String getXh()
    {
        return xh;
    }
    public void setXb(String xb)
    {
        this.xb = xb;
    }

    public String getXb()
    {
        return xb;
    }
    public void setBj(String bj)
    {
        this.bj = bj;
    }

    public String getBj()
    {
        return bj;
    }
    public void setYx(String yx)
    {
        this.yx = yx;
    }

    public String getYx()
    {
        return yx;
    }
    public void setXjZt(String xjZt)
    {
        this.xjZt = xjZt;
    }

    public String getXjZt()
    {
        return xjZt;
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
    public void setSsUserId(Integer ssUserId)
    {
        this.ssUserId = ssUserId;
    }

    public Integer getSsUserId()
    {
        return ssUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("xsjbxxId", getXsjbxxId())
                .append("xm", getXm())
                .append("xh", getXh())
                .append("xb", getXb())
                .append("bj", getBj())
                .append("yx", getYx())
                .append("xjZt", getXjZt())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("ssUserId", getSsUserId())
                .toString();
    }
}
