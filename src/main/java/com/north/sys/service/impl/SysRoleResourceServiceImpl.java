package com.north.sys.service.impl;


import javax.annotation.Resource;

import com.demo.sys.dao.SysResourceDao;
import com.demo.sys.entity.SysResource;
import com.demo.sys.entity.SysResourceExample;
import org.springframework.stereotype.Service;

import com.demo.base.dao.BaseDao;
import com.demo.base.service.impl.BaseServiceImpl;
import com.demo.base.exception.DaoException;
import com.demo.sys.dao.SysRoleResourceDao;
import com.demo.sys.entity.SysRoleResource;
import com.demo.sys.entity.SysRoleResourceExample;
import com.demo.sys.service.SysRoleResourceService;

import java.util.List;

@Service("SysRoleResourceService")
public class SysRoleResourceServiceImpl extends BaseServiceImpl<SysRoleResource, SysRoleResourceExample> implements SysRoleResourceService{

    @Resource
    private SysRoleResourceDao dao;
    @Resource
    private SysResourceDao resourceDao;

    @Override
    public BaseDao<SysRoleResource, SysRoleResourceExample> getDao() throws DaoException {
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
