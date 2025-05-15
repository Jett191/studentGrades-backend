package com.ruoyi.activiti.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色
 */
@Data
public class RoleVo implements Serializable {
    private String roleName;
    private String roleKey;
}
