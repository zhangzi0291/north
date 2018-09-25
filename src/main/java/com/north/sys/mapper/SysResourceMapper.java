package com.north.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.north.sys.entity.SysResource;

import java.util.List;
import java.util.Map;

public interface SysResourceMapper extends BaseMapper<SysResource> {

    List<SysResource> selectMenus(Map<String, Object> param);
    List<SysResource> getResourceMenus(Map<String, Object> param);

}