package com.north.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.north.base.dao.BaseDao;
import com.north.base.service.impl.BaseServiceImpl;
import com.north.sys.dao.SysUserRoleDao;
import com.north.sys.entity.SysUserRole;
import com.north.sys.entity.SysUserRoleExample;
import com.north.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("SysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserRoleDao dao;



    @Override
    public void insertUserRole(Integer userId, Integer roleId) {
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        dao.insert(ur);
    }

    @Override
    public void deleteUserRole(Integer userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        dao.delete(wrapper);
    }

    @Override
    public void updateUserRole(Integer userId, Integer roleId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        dao.delete(wrapper);

        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        dao.insert(ur);
    }
}
