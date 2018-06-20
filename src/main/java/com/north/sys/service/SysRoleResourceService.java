package com.north.sys.service;

import com.demo.base.exception.DaoException;
import com.demo.base.service.BaseService;
import com.demo.sys.entity.SysRoleResource;
import com.demo.sys.entity.SysRoleResourceExample;

import java.util.List;

public interface SysRoleResourceService extends BaseService<SysRoleResource, SysRoleResourceExample> {

    List<SysRoleResource> getResourceByRoleId(Integer roleId);

    void insertRoleResource(Integer roleId, List<Integer> resourceIds);
    void deleteRoleResource(Integer roleId);
    void updateRoleResource(Integer roleId, List<Integer> resourceIds);

}