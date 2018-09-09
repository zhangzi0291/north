package com.north.sys.service;

import com.north.base.service.BaseService;
import com.north.sys.entity.SysUserRole;

public interface SysUserRoleService extends BaseService<SysUserRole> {

    void insertUserRole(String userId, String roleId);
    void deleteUserRole(String userId);
    void updateUserRole(String userId, String roleId);
}