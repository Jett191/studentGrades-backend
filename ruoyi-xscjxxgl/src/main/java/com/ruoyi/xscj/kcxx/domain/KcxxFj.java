package com.ruoyi.xscj.kcxx.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程附件对象 xscj_kcxx_fj
 *
 */
public class KcxxFj extends BaseEntity
{
  private static final long serialVersionUID = 1L;

  /** 附件ID */
  private Integer fj;

  /** 课程信息ID */
  @Excel(name = "课程信息ID")
  private String kcxxId;

  /** 文件名称 */
  @Excel(name = "文件名称")
  private String fileName;

  /** 文件大小 */
  @Excel(name = "文件大小")
  private String fileSize;

  public void setFj(Integer fj)
  {
    this.fj = fj;
  }

  public Integer getFj()
  {
    return fj;
  }
  public void setKcxxId(String kcxxId)
  {
    this.kcxxId = kcxxId;
  }

  public String getKcxxId()
  {
    return kcxxId;
  }
  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  public String getFileName()
  {
    return fileName;
  }
  public void setFileSize(String fileSize)
  {
    this.fileSize = fileSize;
  }

  public String getFileSize()
  {
    return fileSize;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        .append("fj", getFj())
        .append("kcxxId", getKcxxId())
        .append("fileName", getFileName())
        .append("fileSize", getFileSize())
        .toString();
  }
}
