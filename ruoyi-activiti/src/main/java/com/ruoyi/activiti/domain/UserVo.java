// Todo LFN
package com.ruoyi.activiti.domain;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户实体对象
 */
@Data
public class UserVo implements Serializable {
  private String userId;
  private String nickName;
  private String deptName;
}
