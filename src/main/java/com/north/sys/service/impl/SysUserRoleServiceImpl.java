package com.north.sys.service.impl;


import com.north.base.dao.BaseDao;
import com.north.base.service.impl.BaseServiceImpl;
import com.north.sys.dao.SysUserRoleDao;
import com.north.sys.entity.SysUserRole;
import com.north.sys.entity.SysUserRoleExample;
import com.north.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("SysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole, SysUserRoleExample> implements SysUserRoleService {

    @Resource
    private SysUserRoleDao dao;


    @Override
    public BaseDao<SysUserRole, SysUserRoleExample> getDao() {
        return dao;
    }

    @Override
    public void insertUserRole(Integer userId, Integer roleId) {
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        dao.insertSelective(ur);
    }

    @Override
    public void deleteUserRole(Integer userId) {
        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        dao.deleteByExample(example);
    }

    @Override
    public void updateUserRole(Integer userId, Integer roleId) {

        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        dao.deleteByExample(example);

        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        dao.insertSelective(ur);
    }
}
