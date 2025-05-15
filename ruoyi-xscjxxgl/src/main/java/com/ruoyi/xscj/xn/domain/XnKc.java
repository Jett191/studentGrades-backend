// Todo PJX
package com.ruoyi.xscj.xn.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class XnKc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程ID */
    private Integer kcId;

    /** 学年ID */
    @Excel(name = "学年ID")
    private String xnId;

    /** 课程号 */
    @Excel(name = "课程号")
    private String kch;

    /** 课程名 */
    @Excel(name = "课程名")
    private String kcm;

    /** 课程学分 */
    @Excel(name = "课程学分")
    private Integer kcXf;

    /** 任课老师 */
    @Excel(name = "任课老师")
    private String rkLs;

    public void setKcId(Integer kcId)
    {
        this.kcId = kcId;
    }

    public Integer getKcId()
    {
        return kcId;
    }
    public void setXnId(String xnId)
    {
        this.xnId = xnId;
    }

    public String getXnId()
    {
        return xnId;
    }
    public void setKch(String kch)
    {
        this.kch = kch;
    }

    public String getKch()
    {
        return kch;
    }
    public void setKcm(String kcm)
    {
        this.kcm = kcm;
    }

    public String getKcm()
    {
        return kcm;
    }
    public void setKcXf(Integer kcXf)
    {
        this.kcXf = kcXf;
    }

    public Integer getKcXf()
    {
        return kcXf;
    }
    public void setRkLs(String rkLs)
    {
        this.rkLs = rkLs;
    }

    public String getRkLs()
    {
        return rkLs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("kcId", getKcId())
                .append("xnId", getXnId())
                .append("kch", getKch())
                .append("kcm", getKcm())
                .append("kcXf", getKcXf())
                .append("rkLs", getRkLs())
                .toString();
    }
}
