package com.north.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.R;
import com.north.sys.entity.*;
import com.north.sys.service.SysResourceService;
import com.north.sys.service.SysRoleResourceService;
import com.north.sys.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/role")
public class SysRoleController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysRoleResourceService sysRoleResourceService;
    @Resource
    private SysResourceService sysResourceService;
    
    @RequestMapping("list")
    public R listJson(SysRole sysRole, Page page){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();

        wrapper.orderByAsc("id");

        if(!StringUtils.isEmpty(sysRole.getRoleName())){
            wrapper.like("role_name",sysRole.getRoleName());
        }
        try {
            IPage<SysRole> pages = sysRoleService.selectByWrapperAndPage(page,wrapper);
            return R.ok().putObject("rows",pages.getRecords()).putObject("total", pages.getTotal());
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping("selectOptions")
    public R selectOptions(SysRole sysRole){
        try {
            List< SysRole> list = sysRoleService.selectByWrapper(new QueryWrapper<>());
            List<Map<String,Object>> options = new ArrayList<>();
            list.forEach(role->{
                Map<String,Object> map = new HashMap<>();
                map.put("name",role.getRoleName());
                map.put("value",role.getId());
                options.add(map);
            });
            return R.ok().putObject("data",options);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping("add")
    public R addJson(SysRole sysRole ,String resources){
    	Integer num = 0;
        try {
            num = sysRoleService.insert(sysRole);
            List<String> resourceIdList = getResourceIdList(JSONArray.parseArray(resources,SysResource.class));
            sysRoleResourceService.insertRoleResource(sysRole.getId(), resourceIdList);
            if(num==0){
                return R.error("保存失败,无数据");
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
    
   	@RequestMapping("get")
    public R get(String id){
        try {
            List<SysRoleResource> rrlist =  sysRoleResourceService.getResourceByRoleId(id);
            List<SysResource> list = sysResourceService.getResourceMenus(null);
            List<SysResourceDto> options = setChildNood(list,rrlist);
            return R.ok("data",sysRoleService.selectById(id)).putObject("options",options);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }


    private List<SysResourceDto> setChildNood(List<SysResource> list,List<SysRoleResource> rrlist) {
        List<SysResourceDto> options = new ArrayList<>();
        list.forEach(r -> {
            SysResourceDto dto = new SysResourceDto();
            BeanUtils.copyProperties(r, dto);
            if(!CollectionUtils.isEmpty(rrlist)) {
                for (SysRoleResource rr : rrlist){
                    if (rr.getResourceId() == r.getId()) {
                        dto.setChecked(true);
                        break;
                    }else{
                        dto.setChecked(false);
                    }

                }
            }else{
                dto.setChecked(false);
            }
            if (!CollectionUtils.isEmpty(r.getChild())) {
                dto.setChildren(setChildNood(r.getChild(),rrlist));
            }
            options.add(dto);
        });

        return options;
    }

    @RequestMapping("edit")
    public R editJson(SysRole sysRole,String resources){
   		Integer num = 0;
        try {
            num = sysRoleService.updateById(sysRole);
            List<String> resourceIdList = getResourceIdList(JSONArray.parseArray(resources,SysResource.class));
            sysRoleResourceService.updateRoleResource(sysRole.getId(), resourceIdList);
            if(num==0){
                return R.error("保存失败,无数据");
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
    
    @RequestMapping("del")
    public R delJson(Map<String, Object> map, @RequestParam("ids") List<String> ids ){
        Integer num = 0;
        try {
            for(int i=0;i<ids.size();i++){
                num+=sysRoleService.deleteById(ids.get(i));
                sysRoleResourceService.deleteRoleResource(ids.get(i));
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }


    private List<String> getResourceIdList(List<SysResource> resourceList){
        List<String> resourceIdList = new ArrayList<>();
        resourceList.forEach(resource ->{
            resourceIdList.add(resource.getId());
        });
        return resourceIdList;
    }
}