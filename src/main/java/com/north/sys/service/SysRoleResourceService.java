package com.north.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.north.sys.entity.SysRoleResource;

import java.util.List;

public interface SysRoleResourceService extends IService<SysRoleResource> {

    List<SysRoleResource> getResourceByRoleId(String roleId);

    void insertRoleResource(String roleId, List<String> resourceIds);
    void deleteRoleResource(List<String> roleIds);
    void updateRoleResource(String roleId, List<String> resourceIds);

}