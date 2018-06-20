package com.north.sys.service.impl;


import javax.annotation.Resource;

import com.demo.sys.entity.*;
import com.demo.sys.service.SysResourceService;
import org.springframework.stereotype.Service;

import com.demo.base.dao.BaseDao;
import com.demo.base.service.impl.BaseServiceImpl;
import com.demo.base.exception.DaoException;
import com.demo.sys.dao.SysResourceDao;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("SysResourceervice")
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource, SysResourceExample> implements SysResourceService{

    @Resource
    private SysResourceDao dao;


    @Override
    public BaseDao<SysResource, SysResourceExample> getDao() throws DaoException {
        return dao;
    }


    @Override
    public List<SysResource> getAllResource() {
        SysResourceExample example = new SysResourceExample();
        return dao.selectByExample(example);
    }

    @Override
    public List<SysResource> getResourceMenus(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", id);
        List<SysResource> list = dao.getResourceMenus(param);
        return setChildNood(list,null);
    }

    @Override
    public List<SysResource> getMenus(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", id);
        List<SysResource> list = dao.selectMenus(param);
        return setChildNood(list,null);
    }

    @Override
    public List<SysResource> getMenusByRoleId(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("roleId", id);
        List<SysResource> list = dao.selectMenus(param);
        return setChildNood(list,null);
    }

    private List<SysResource> setChildNood(List<SysResource> resourceList, List<SysResource> nextNodeList){
        if(!CollectionUtils.isEmpty(resourceList)) {
            if (CollectionUtils.isEmpty(nextNodeList)) {
                List<SysResource> child = resourceList.stream().sorted(Comparator.comparing(SysResource::getResourceOrderNum))
                        .filter(s -> s.getParentId().equals(-1)).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(child)){
                    return resourceList;
                }
                resourceList.removeAll(child);
                nextNodeList = child;
                setChildNood(resourceList, child);
            } else {
                nextNodeList.forEach(p -> {
                    List<SysResource> child = resourceList.stream().sorted(Comparator.comparing(SysResource::getResourceOrderNum))
                            .filter(s -> s.getParentId().equals(p.getId())).collect(Collectors.toList());
                    p.setChild(child);
                    if(CollectionUtils.isEmpty(child)){
                        return;
                    }
                    resourceList.removeAll(child);
                    setChildNood(resourceList, child);
                });
            }
        }
        return nextNodeList;
    }
}
