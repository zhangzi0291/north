package com.north.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.north.sys.entity.SysResource;

import java.util.List;

public interface SysResourceService extends IService<SysResource> {

    List<SysResource> getAllResource() ;

    List<SysResource> getMenus(String id) ;

    List<SysResource> getResourceMenus(String id) ;

    List<SysResource> getMenusByRoleId(String id) ;

}