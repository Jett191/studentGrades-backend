// Todo WJS
// Todo LJY
// Todo SJB

package com.ruoyi.xscj.cjlr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cjlr extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 成绩录入ID */
    private String cjlrId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String xsName;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String kcName;

    /** 课程成绩 */
    @Excel(name = "课程成绩")
    private Integer kcCj;

    /** 用户ID */
    private Integer userId;

    /** 部门ID */
    private Integer deptId;

    private Integer xghCj;
}
