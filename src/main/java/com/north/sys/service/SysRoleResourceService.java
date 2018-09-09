package com.north.sys.service;

import com.north.base.service.BaseService;
import com.north.sys.entity.SysRoleResource;

import java.util.List;

public interface SysRoleResourceService extends BaseService<SysRoleResource> {

    List<SysRoleResource> getResourceByRoleId(String roleId);

    void insertRoleResource(String roleId, List<String> resourceIds);
    void deleteRoleResource(String roleId);
    void updateRoleResource(String roleId, List<String> resourceIds);

}