package com.north.sys.service.impl;


import com.north.base.dao.BaseDao;
import com.north.base.service.impl.BaseServiceImpl;
import com.north.sys.dao.SysRoleDao;
import com.north.sys.dao.SysUserDao;
import com.north.sys.dao.SysUserRoleDao;
import com.north.sys.entity.*;
import com.north.sys.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service("SysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserExample> implements SysUserService {

    @Resource
    private SysUserDao dao;
    @Resource
    private SysUserRoleDao userRoleDao;
    @Resource
    private SysRoleDao roleDao;

    @Override
    public BaseDao<SysUser, SysUserExample> getDao()  {
        return dao;
    }

    @Override
    public SysUser findByName(String username) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andusernameEqualTo(username);
        List<SysUser> userList = dao.selectByExample(example);
        if(!CollectionUtils.isEmpty(userList)){
            SysUser user = userList.get(0);
            SysUserRoleExample example1 = new SysUserRoleExample();
            SysUserRoleExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andUserIdEqualTo(user.getId());
            List<SysUserRole> ur = userRoleDao.selectByExample(example1);
            if(!CollectionUtils.isEmpty(ur)){
                SysRoleExample example2 = new SysRoleExample();
                SysRoleExample.Criteria criteria2 = example2.createCriteria();
                criteria2.andidEqualTo(ur.get(0).getRoleId());
                List<SysRole> roleList = roleDao.selectByExample(example2);
                user.setRoleList(roleList);
            }
            return user;
        }
        return null;
    }

    @Override
    public SysUserDto getUserAndRoleId(Integer userId) {
        SysUser userinfo = dao.selectByPrimaryKey(userId);
        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<SysUserRole> ur = userRoleDao.selectByExample(example);
        Assert.isTrue(!CollectionUtils.isEmpty(ur),"用户没有角色");
        SysUserDto user = new SysUserDto(userinfo,ur.get(0).getRoleId());
        return user;
    }
}
