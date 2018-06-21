package com.north.sys.service.impl;


import com.north.base.dao.BaseDao;
import com.north.base.service.impl.BaseServiceImpl;
import com.north.sys.dao.SysRoleDao;
import com.north.sys.dao.SysRoleResourceDao;
import com.north.sys.entity.SysRole;
import com.north.sys.entity.SysRoleExample;
import com.north.sys.entity.SysRoleResource;
import com.north.sys.entity.SysRoleResourceExample;
import com.north.sys.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("SysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, SysRoleExample> implements SysRoleService {

    @Resource
    private SysRoleDao dao;
    @Resource
    private SysRoleResourceDao roleResourceDao;

    @Override
    public BaseDao<SysRole, SysRoleExample> getDao()  {
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
