package com.north.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.north.base.service.impl.BaseServiceImpl;
import com.north.sys.dao.SysRoleDao;
import com.north.sys.dao.SysRoleResourceDao;
import com.north.sys.entity.SysRole;
import com.north.sys.entity.SysRoleResource;
import com.north.sys.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("SysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Resource
    private SysRoleDao dao;
    @Resource
    private SysRoleResourceDao roleResourceDao;


    @Override
    public List<SysRole> getRoleByResourceId(int resourceId) {
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("resource_id",resourceId);

        List<SysRoleResource> roleResourceList =  roleResourceDao.selectList(wrapper);

        List<Integer> roleIdList = new ArrayList<>();
        for(SysRoleResource rr:roleResourceList){
            roleIdList.add(rr.getRoleId());
        }
        if(CollectionUtils.isEmpty(roleIdList)){
            return new ArrayList<>();
        }

        return dao.selectBatchIds(roleIdList);
    }
}
