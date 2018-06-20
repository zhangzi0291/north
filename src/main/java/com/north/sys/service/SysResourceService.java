package com.north.sys.service;

import com.demo.base.service.BaseService;
import com.demo.sys.entity.SysResource;
import com.demo.sys.entity.SysResourceExample;

import java.util.List;

public interface SysResourceService extends BaseService<SysResource, SysResourceExample> {

    List<SysResource> getAllResource() ;

    List<SysResource> getMenus(Integer id) ;

    List<SysResource> getResourceMenus(Integer id) ;

    List<SysResource> getMenusByRoleId(Integer id) ;

}