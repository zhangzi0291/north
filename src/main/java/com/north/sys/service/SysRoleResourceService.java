package com.north.sys.service;

import com.north.base.service.BaseService;
import com.north.sys.entity.SysRoleResource;
import com.north.sys.entity.SysRoleResourceExample;

import java.util.List;

public interface SysRoleResourceService extends BaseService<SysRoleResource, SysRoleResourceExample> {

    List<SysRoleResource> getResourceByRoleId(Integer roleId);

    void insertRoleResource(Integer roleId, List<Integer> resourceIds);
    void deleteRoleResource(Integer roleId);
    void updateRoleResource(Integer roleId, List<Integer> resourceIds);

}