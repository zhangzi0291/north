package com.north.sys.service;

import com.north.base.service.BaseService;
import com.north.sys.entity.SysUser;
import com.north.sys.entity.SysUserDto;

public interface SysUserService extends BaseService<SysUser> {

    SysUser findByName(String username);
    SysUserDto getUserAndRoleId(Integer userId);

}