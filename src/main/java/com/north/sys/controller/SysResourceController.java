package com.north.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.R;
import com.north.sys.entity.SysResource;
import com.north.sys.entity.SysResourceDto;
import com.north.sys.service.SysResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("sys/menu")
public class SysResourceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysResourceService sysResourceService;

    @RequestMapping(path = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public R listJson(SysResource sysResource, Page page) {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        //设置查询条件
        if (!StringUtils.isEmpty(sysResource.getResourceName())) {
            wrapper.like("resource_name",sysResource.getResourceName().trim());
        }
        if (!StringUtils.isEmpty(sysResource.getResourceType())) {
            wrapper.like("resource_type",sysResource.getResourceType().trim());
        }
        if (!StringUtils.isEmpty(sysResource.getResourceUrl())) {
            wrapper.like("resource_url",sysResource.getResourceUrl().trim());
        }
        try {
            IPage<SysResource> pages = sysResourceService.page(page,wrapper);
            return R.ok().putObject("rows", pages.getRecords()).putObject("total", pages.getTotal());
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping(path = "getAllMenu", method = {RequestMethod.GET, RequestMethod.POST})
    public R getAllMenu(SysResource sysResource) {
        //设置查询条件 。。。

        try {
            List<SysResource> list = sysResourceService.getResourceMenus(null);
            List<SysResourceDto> options = setChildNood(list);
            return R.ok().putObject("data", options);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    private List<SysResourceDto> setChildNood(List<SysResource> list) {
        List<SysResourceDto> options = new ArrayList<>();
        list.forEach(r -> {
            SysResourceDto dto = new SysResourceDto();
            BeanUtils.copyProperties(r, dto);
            if (!CollectionUtils.isEmpty(r.getChild())) {
                dto.setChildren(setChildNood(r.getChild()));
            }
            options.add(dto);
        });

        return options;
    }

    @RequestMapping(path = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public R addJson(SysResource sysResource) {
        Boolean flag = false;
        try {
            flag = sysResourceService.save(sysResource);
            if (!flag) {
                return R.error("保存失败,无数据");
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data", flag);
    }

    @RequestMapping(path = "get", method = {RequestMethod.GET, RequestMethod.POST})
    public R get(String id) {
        try {
            return R.ok("data", sysResourceService.getById(id));
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping(path = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public R editJson(Map<String, Object> map, SysResource sysResource) {
        Boolean flag = false;
        try {
            flag = sysResourceService.updateById(sysResource);
            if (!flag) {
                return R.error("保存失败,无数据");
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data", flag);
    }

    @RequestMapping(path = "del", method = {RequestMethod.GET, RequestMethod.POST})
    public R delJson(Map<String, Object> map, @RequestParam("ids") List<Integer> ids) {
        Boolean flag = false;
        try {
            flag = sysResourceService.removeByIds(ids);
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data", flag);
    }
}