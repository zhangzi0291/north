package com.north.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.north.sys.entity.SysUser;
import com.north.sys.entity.SysUserDto;

public interface SysUserService extends IService<SysUser> {

    SysUser findByName(String username);
    SysUserDto getUserAndRoleId(String userId);

}