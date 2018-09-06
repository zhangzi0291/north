package com.north.sys.service;

import com.north.base.service.BaseService;
import com.north.sys.entity.SysRole;
import com.north.sys.entity.SysRoleExample;

import java.util.List;

public interface SysRoleService extends BaseService<SysRole> {

    List<SysRole> getRoleByResourceId(int resourceId) ;

}