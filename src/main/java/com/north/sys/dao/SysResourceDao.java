package com.north.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.north.base.dao.BaseDao;
import com.north.sys.entity.SysResource;
import com.north.sys.entity.SysResourceExample;

import java.util.List;
import java.util.Map;

public interface SysResourceDao extends BaseMapper<SysResource> {

    List<SysResource> selectMenus(Map<String, Object> param);
    List<SysResource> getResourceMenus(Map<String, Object> param);

}