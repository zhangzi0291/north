package com.north.sys.service;

import com.north.base.service.BaseService;
import com.north.sys.entity.SysResource;

import java.util.List;

public interface SysResourceService extends BaseService<SysResource> {

    List<SysResource> getAllResource() ;

    List<SysResource> getMenus(Integer id) ;

    List<SysResource> getResourceMenus(Integer id) ;

    List<SysResource> getMenusByRoleId(Integer id) ;

}