package com.north.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.north.sys.mapper.SysUserRoleMapper;
import com.north.sys.entity.SysUserRole;
import com.north.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper,SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper dao;

    @Override
    public void insertUserRole(String userId, String roleId) {
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        dao.insert(ur);
    }

    @Override
    public void deleteUserRole(List<String> userIds) {
        for(String userId:userIds) {
            QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            dao.delete(wrapper);
        }
    }

    @Override
    public void updateUserRole(String userId, String roleId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        dao.delete(wrapper);

        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        dao.insert(ur);
    }
}
