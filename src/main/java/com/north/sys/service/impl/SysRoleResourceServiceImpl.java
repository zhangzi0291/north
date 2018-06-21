package com.north.sys.service.impl;


import com.north.base.dao.BaseDao;
import com.north.base.service.impl.BaseServiceImpl;
import com.north.sys.dao.SysResourceDao;
import com.north.sys.dao.SysRoleResourceDao;
import com.north.sys.entity.SysResource;
import com.north.sys.entity.SysResourceExample;
import com.north.sys.entity.SysRoleResource;
import com.north.sys.entity.SysRoleResourceExample;
import com.north.sys.service.SysRoleResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SysRoleResourceService")
public class SysRoleResourceServiceImpl extends BaseServiceImpl<SysRoleResource, SysRoleResourceExample> implements SysRoleResourceService {

    @Resource
    private SysRoleResourceDao dao;
    @Resource
    private SysResourceDao resourceDao;

    @Override
    public BaseDao<SysRoleResource, SysRoleResourceExample> getDao() {
        return dao;
    }


    @Override
    public List<SysRoleResource> getResourceByRoleId(Integer roleId) {
        SysRoleResourceExample example = new SysRoleResourceExample();
        SysRoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        return dao.selectByExample(example);
    }

    @Override
    public void insertRoleResource(Integer roleId, List<Integer> resourceIds) {
        resourceIds.forEach(resourceId ->{
            SysRoleResource rr = new SysRoleResource();
            rr.setRoleId(roleId);
            rr.setResourceId(resourceId);
            dao.insertSelective(rr);

            SysResourceExample example = new SysResourceExample();
            SysResourceExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(resourceId);
            List<SysResource> resourceList = resourceDao.selectByExample(example);
            resourceList.forEach(resource->{
                SysRoleResource rr2 = new SysRoleResource();
                rr2.setRoleId(roleId);
                rr2.setResourceId(resource.getId());
                dao.insertSelective(rr2);
            });
        });
    }

    @Override
    public void deleteRoleResource(Integer roleId) {
        SysRoleResourceExample example = new SysRoleResourceExample();
        SysRoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        dao.deleteByExample(example);
    }

    @Override
    public void updateRoleResource(Integer roleId, List<Integer> resourceIds) {

        SysRoleResourceExample example = new SysRoleResourceExample();
        SysRoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        dao.deleteByExample(example);

        resourceIds.forEach(resourceId ->{
            SysRoleResource rr = new SysRoleResource();
            rr.setRoleId(roleId);
            rr.setResourceId(resourceId);
            dao.insertSelective(rr);
            SysResourceExample example2 = new SysResourceExample();
            SysResourceExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andParentIdEqualTo(resourceId);
            List<SysResource> resourceList = resourceDao.selectByExample(example2);
            resourceList.forEach(resource->{
                SysRoleResource rr2 = new SysRoleResource();
                rr2.setRoleId(roleId);
                rr2.setResourceId(resource.getId());
                dao.insertSelective(rr2);
            });
        });
    }
}
