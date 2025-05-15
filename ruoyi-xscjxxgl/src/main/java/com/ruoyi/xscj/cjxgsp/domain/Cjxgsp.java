// Todo SJB
package com.ruoyi.xscj.cjxgsp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 成绩修改审批对象 xscj_cjxgsp
 * @author sjba1
 * @date 2025/4/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cjxgsp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 成绩修改审批ID */
    private Integer cjxgspId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String xsName;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String kcName;

    /** 修改前成绩 */
    @Excel(name = "修改前成绩")
    private Integer xgqCj;

    /** 修改后成绩 */
    @Excel(name = "修改后成绩")
    private Integer xghCj;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String spZt;

    /** 用户ID */
    private Integer userId;

    //成绩录入ID
    private String cjlrId;

}
