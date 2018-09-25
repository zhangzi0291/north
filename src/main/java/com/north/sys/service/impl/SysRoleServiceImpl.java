package com.north.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.north.sys.mapper.SysRoleMapper;
import com.north.sys.mapper.SysRoleResourceMapper;
import com.north.sys.entity.SysRole;
import com.north.sys.entity.SysRoleResource;
import com.north.sys.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("SysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper dao;
    @Resource
    private SysRoleResourceMapper roleResourceDao;


    @Override
    public List<SysRole> getRoleByResourceId(int resourceId) {
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("resource_id",resourceId);

        List<SysRoleResource> roleResourceList =  roleResourceDao.selectList(wrapper);

        List<String> roleIdList = new ArrayList<>();
        for(SysRoleResource rr:roleResourceList){
            roleIdList.add(rr.getRoleId());
        }
        if(CollectionUtils.isEmpty(roleIdList)){
            return new ArrayList<>();
        }

        return dao.selectBatchIds(roleIdList);
    }
}
