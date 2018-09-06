package com.north.sys.service;

import com.north.base.service.BaseService;
import com.north.sys.entity.SysUserRole;
import com.north.sys.entity.SysUserRoleExample;

public interface SysUserRoleService extends BaseService<SysUserRole> {

    void insertUserRole(Integer userId, Integer roleId);
    void deleteUserRole(Integer userId);
    void updateUserRole(Integer userId, Integer roleId);
}