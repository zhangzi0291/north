package com.north.sys.service;

import com.demo.base.service.BaseService;
import com.demo.sys.entity.SysRole;
import com.demo.sys.entity.SysRoleExample;

import java.util.List;

public interface SysRoleService extends BaseService<SysRole, SysRoleExample> {

    List<SysRole> getRoleByResourceId(int resourceId) ;

}