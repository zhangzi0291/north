package com.north.sys.entity;

import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

public class SysUserDto extends SysUser{

    private Integer roleId;

    public SysUserDto() {

    }

    public SysUserDto(SysUser user, Integer roleId) {
        BeanUtils.copyProperties(user,this);
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}