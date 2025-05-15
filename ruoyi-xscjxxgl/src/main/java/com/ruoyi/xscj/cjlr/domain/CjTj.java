// Todo WJS
// Todo LJY
// Todo SJB

package com.ruoyi.xscj.cjlr.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CjTj extends BaseEntity {

    private String kcName;
    private Double pjf;
    private Double zgf;
    private Double zdf;

}
