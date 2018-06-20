package com.north.sys.dao;

import com.demo.base.dao.BaseDao;
import com.demo.sys.entity.SysResource;
import com.demo.sys.entity.SysResourceExample;

import java.util.List;
import java.util.Map;

public interface SysResourceDao extends BaseDao<SysResource, SysResourceExample> {

    List<SysResource> selectMenus(Map<String, Object> param);
    List<SysResource> getResourceMenus(Map<String, Object> param);

}