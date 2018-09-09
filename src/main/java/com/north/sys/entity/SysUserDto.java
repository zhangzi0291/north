package com.north.sys.entity;

import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

public class SysUserDto extends SysUser{

    private String roleId;

    public SysUserDto() {

    }

    public SysUserDto(SysUser user, String roleId) {
        BeanUtils.copyProperties(user,this);
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}