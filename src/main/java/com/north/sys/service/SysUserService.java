package com.north.sys.service;

import com.demo.base.service.BaseService;
import com.demo.sys.entity.SysUser;
import com.demo.sys.entity.SysUserDto;
import com.demo.sys.entity.SysUserExample;

public interface SysUserService extends BaseService<SysUser, SysUserExample> {

    SysUser findByName(String username);
    SysUserDto getUserAndRoleId(Integer userId);

}