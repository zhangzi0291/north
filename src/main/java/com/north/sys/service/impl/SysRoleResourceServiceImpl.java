package com.north.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.north.base.service.impl.BaseServiceImpl;
import com.north.sys.dao.SysResourceDao;
import com.north.sys.dao.SysRoleResourceDao;
import com.north.sys.entity.SysResource;
import com.north.sys.entity.SysRoleResource;
import com.north.sys.service.SysRoleResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SysRoleResourceService")
public class SysRoleResourceServiceImpl extends BaseServiceImpl<SysRoleResource> implements SysRoleResourceService {

    @Resource
    private SysRoleResourceDao dao;
    @Resource
    private SysResourceDao resourceDao;


    @Override
    public List<SysRoleResource> getResourceByRoleId(String roleId) {
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        return dao.selectList(wrapper);
    }

    @Override
    public void insertRoleResource(String roleId, List<String> resourceIds) {
        resourceIds.forEach(resourceId ->{
            SysRoleResource rr = new SysRoleResource();
            rr.setRoleId(roleId);
            rr.setResourceId(resourceId);
            dao.insert(rr);

            QueryWrapper< SysResource> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id",resourceId);

            List<SysResource> resourceList = resourceDao.selectList(wrapper);
            resourceList.forEach(resource->{
                SysRoleResource rr2 = new SysRoleResource();
                rr2.setRoleId(roleId);
                rr2.setResourceId(resource.getId());
                dao.insert(rr2);
            });
        });
    }

    @Override
    public void deleteRoleResource(String roleId) {
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        dao.delete(wrapper);
    }

    @Override
    public void updateRoleResource(String roleId, List<String> resourceIds) {

        deleteRoleResource(roleId);

        insertRoleResource(roleId,resourceIds);

    }
}
