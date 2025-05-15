package com.ruoyi.xscj.kcxx.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程信息对象 xscj_kcxx
 *
 */
public class Kcxx extends BaseEntity
{
  private static final long serialVersionUID = 1L;

  /** 课程信息ID */
  private String kcxxId;

  /** 课程名称 */
  @Excel(name = "课程名称")
  private String kcName;

  /** 课程代码 */
  @Excel(name = "课程代码")
  private String kcDm;

  /** 学分 */
  @Excel(name = "学分")
  private Integer xf;

  /** 课程类型 */
  @Excel(name = "课程类型")
  private String kcLx;

  /** 用户ID */
  private Integer userId;

  /** 部门ID */
  private Integer deptId;

  /** 课程附件信息 */
  private List<KcxxFj> kcxxFjList;

  public Integer getDeleted() {
    return deleted;
  }

  public void setDeleted(Integer deleted) {
    this.deleted = deleted;
  }

  private Integer deleted;

  public void setKcxxId(String kcxxId)
  {
    this.kcxxId = kcxxId;
  }

  public String getKcxxId()
  {
    return kcxxId;
  }
  public void setKcName(String kcName)
  {
    this.kcName = kcName;
  }

  public String getKcName()
  {
    return kcName;
  }
  public void setKcDm(String kcDm)
  {
    this.kcDm = kcDm;
  }

  public String getKcDm()
  {
    return kcDm;
  }
  public void setXf(Integer xf)
  {
    this.xf = xf;
  }

  public Integer getXf()
  {
    return xf;
  }
  public void setKcLx(String kcLx)
  {
    this.kcLx = kcLx;
  }

  public String getKcLx()
  {
    return kcLx;
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

  public List<KcxxFj> getKcxxFjList()
  {
    return kcxxFjList;
  }

  public void setKcxxFjList(List<KcxxFj> kcxxFjList)
  {
    this.kcxxFjList = kcxxFjList;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        .append("kcxxId", getKcxxId())
        .append("kcName", getKcName())
        .append("kcDm", getKcDm())
        .append("xf", getXf())
        .append("kcLx", getKcLx())
        .append("createBy", getCreateBy())
        .append("createTime", getCreateTime())
        .append("updateBy", getUpdateBy())
        .append("updateTime", getUpdateTime())
        .append("userId", getUserId())
        .append("deptId", getDeptId())
        .append("kcxxFjList", getKcxxFjList())
        .toString();
  }

}
