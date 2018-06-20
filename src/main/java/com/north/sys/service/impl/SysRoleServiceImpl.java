package com.north.sys.service.impl;


import javax.annotation.Resource;

import com.demo.sys.dao.SysRoleResourceDao;
import com.demo.sys.entity.SysRoleResource;
import com.demo.sys.entity.SysRoleResourceExample;
import org.springframework.stereotype.Service;

import com.demo.base.dao.BaseDao;
import com.demo.base.service.impl.BaseServiceImpl;
import com.demo.base.exception.DaoException;
import com.demo.sys.dao.SysRoleDao;
import com.demo.sys.entity.SysRole;
import com.demo.sys.entity.SysRoleExample;
import com.demo.sys.service.SysRoleService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service("SysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, SysRoleExample> implements SysRoleService{

    @Resource
    private SysRoleDao dao;
    @Resource
    private SysRoleResourceDao roleResourceDao;

    @Override
    public BaseDao<SysRole, SysRoleExample> getDao() throws DaoException {
        return dao;
    }

    @Override
    public List<SysRole> getRoleByResourceId(int resourceId) {
        SysRoleResourceExample roleResourceExample = new SysRoleResourceExample();
        SysRoleResourceExample.Criteria roleResourceCriteria = roleResourceExample.createCriteria();
        roleResourceCriteria.andResourceIdEqualTo(resourceId);
        List<SysRoleResource> roleResourceList =  roleResourceDao.selectByExample(roleResourceExample);

        List<Integer> roleIdList = new ArrayList<>();
        for(SysRoleResource rr:roleResourceList){
            roleIdList.add(rr.getRoleId());
        }
        if(CollectionUtils.isEmpty(roleIdList)){
            return new ArrayList<>();
        }
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        criteria.andidIn(roleIdList);

        return dao.selectByExample(example);
    }
}
