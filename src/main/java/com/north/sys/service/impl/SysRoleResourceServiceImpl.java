package com.north.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.north.sys.mapper.SysResourceMapper;
import com.north.sys.mapper.SysRoleResourceMapper;
import com.north.sys.entity.SysResource;
import com.north.sys.entity.SysRoleResource;
import com.north.sys.service.SysRoleResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("SysRoleResourceService")
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper,SysRoleResource> implements SysRoleResourceService {

    @Resource
    private SysRoleResourceMapper dao;
    @Resource
    private SysResourceMapper resourceDao;


    @Override
    public List<SysRoleResource> getResourceByRoleId(String roleId) {
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        return dao.selectList(wrapper);
    }

    @Override
    public void insertRoleResource(String roleId, List<String> resourceIds) {
        Set<SysRoleResource> rrList = new HashSet<>();

        resourceIds.forEach(resourceId ->{
            SysRoleResource rr = new SysRoleResource();
            rr.setRoleId(roleId);
            rr.setResourceId(resourceId);
            rrList.add(rr);

            QueryWrapper< SysResource> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id",resourceId);
            List<SysResource> resourceList = resourceDao.selectList(wrapper);
            resourceList.forEach(resource->{
                SysRoleResource rr2 = new SysRoleResource();
                rr2.setRoleId(roleId);
                rr2.setResourceId(resource.getId());
                rrList.add(rr2);
            });

        });
        this.saveBatch(rrList);
    }

    @Override
    public void deleteRoleResource(List<String> roleIds) {
        for(String roleId:roleIds) {
            QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id", roleId);
            dao.delete(wrapper);
        }
    }

    @Override
    public void updateRoleResource(String roleId, List<String> resourceIds) {
        List<String> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        deleteRoleResource(roleIds);
        insertRoleResource(roleId,resourceIds);
    }
}
