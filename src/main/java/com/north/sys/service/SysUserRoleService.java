package com.north.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.north.sys.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {

    void insertUserRole(String userId, String roleId);
    void deleteUserRole(List<String> userId);
    void updateUserRole(String userId, String roleId);
}