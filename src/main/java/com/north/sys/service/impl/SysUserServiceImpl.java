package com.north.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.north.sys.mapper.SysRoleMapper;
import com.north.sys.mapper.SysUserMapper;
import com.north.sys.mapper.SysUserRoleMapper;
import com.north.sys.entity.SysRole;
import com.north.sys.entity.SysUser;
import com.north.sys.entity.SysUserDto;
import com.north.sys.entity.SysUserRole;
import com.north.sys.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("SysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Resource
    private SysUserMapper dao;
    @Resource
    private SysUserRoleMapper userRoleDao;
    @Resource
    private SysRoleMapper roleDao;

    @Override
    public SysUser findByName(String username) {
        SysUser user = dao.selectOne(new QueryWrapper<SysUser>().eq("username",username));
        List<SysRole> roleList = new ArrayList<>();
        if(user != null){
            QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",user.getId());
            List<SysUserRole> urs = userRoleDao.selectList(wrapper);
            if(!CollectionUtils.isEmpty(urs)){
                for(SysUserRole ur:urs){
                    roleList.add(roleDao.selectById(ur.getRoleId()));
                }
            }
            user.setRoleList(roleList);
            return user;
        }
        return null;
    }

    @Override
    public SysUserDto getUserAndRoleId(String userId) {
        SysUser userinfo = dao.selectById(userId);

        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> ur = userRoleDao.selectList(wrapper);

        Assert.isTrue(!CollectionUtils.isEmpty(ur),"用户没有角色");

        SysUserDto user = new SysUserDto(userinfo,ur.get(0).getRoleId());
        return user;
    }

}
