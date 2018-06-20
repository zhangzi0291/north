package com.north.sys.service;

import com.demo.base.service.BaseService;
import com.demo.sys.entity.SysUserRole;
import com.demo.sys.entity.SysUserRoleExample;

public interface SysUserRoleService extends BaseService<SysUserRole, SysUserRoleExample> {

    void insertUserRole(Integer userId, Integer roleId);
    void deleteUserRole(Integer userId);
    void updateUserRole(Integer userId, Integer roleId);
}