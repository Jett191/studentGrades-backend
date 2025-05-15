// Todo LFN
package com.ruoyi.activiti.mapper;

import com.ruoyi.activiti.domain.RoleVo;
import com.ruoyi.activiti.domain.UserVo;
import java.util.List;

public interface UserMapper {

  /**
   * 查询用户列表数据
   * @return
   */
  List<UserVo> listUsersToWorkflow();

  /**
   * 查询角色列表
   * @return
   */
  List<RoleVo> queryAllSysRole();
}
