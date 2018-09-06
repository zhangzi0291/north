package com.north.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.north.base.Page;
import com.north.base.service.BaseService;
import com.north.sys.entity.SysUser;
import com.north.sys.entity.SysUserDto;
import com.north.sys.entity.SysUserExample;

import java.io.Serializable;
import java.util.List;

public interface SysUserService extends BaseService<SysUser> {

    SysUser findByName(String username);
    SysUserDto getUserAndRoleId(Integer userId);

}