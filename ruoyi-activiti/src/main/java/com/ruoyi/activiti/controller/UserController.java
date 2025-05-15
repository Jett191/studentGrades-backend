// Todo LFN
package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.domain.RoleVo;
import com.ruoyi.activiti.domain.UserVo;
import com.ruoyi.activiti.mapper.UserMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserMapper userMapper;

  /**
   * 查询用户数据
   *
   * @return
   */
  @RequestMapping(value = "/listUsersToWorkflow", method = RequestMethod.GET)
  public AjaxResult listUsersToWorkflow() {
    List<UserVo> userVos = userMapper.listUsersToWorkflow();
    return AjaxResult.success(userVos);
  }

  /**
   * 查询角色数据
   *
   * @return
   */
  @RequestMapping(value = "/queryAllSysRole", method = RequestMethod.GET)
  public AjaxResult queryAllSysRole() {
    List<RoleVo> roleVos = userMapper.queryAllSysRole();
    return AjaxResult.success(roleVos);
  }

}
