// Todo SJB
package com.ruoyi.xscj.kcpj.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程评价对象 xscj_kcpj
 *
 */
public class Kcpj extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程评价ID */
    private String kcpjId;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String kcName;

    /** 课程评分 */
    @Excel(name = "课程评分")
    private Integer kcPf;

    /** 课程反馈 */
    @Excel(name = "课程反馈")
    private String kcFk;

    /** 用户ID */
    private Integer userId;

    /** 部门ID */
    private Integer deptId;

    public void setKcpjId(String kcpjId)
    {
        this.kcpjId = kcpjId;
    }

    public String getKcpjId()
    {
        return kcpjId;
    }
    public void setKcName(String kcName)
    {
        this.kcName = kcName;
    }

    public String getKcName()
    {
        return kcName;
    }
    public void setKcPf(Integer kcPf)
    {
        this.kcPf = kcPf;
    }

    public Integer getKcPf()
    {
        return kcPf;
    }
    public void setKcFk(String kcFk)
    {
        this.kcFk = kcFk;
    }

    public String getKcFk()
    {
        return kcFk;
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
                .append("kcpjId", getKcpjId())
                .append("kcName", getKcName())
                .append("kcPf", getKcPf())
                .append("kcFk", getKcFk())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .toString();
    }
}
