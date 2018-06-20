package com.north.sys.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.demo.base.R;
import com.demo.sys.entity.*;
import com.demo.utils.CamelToUnderlineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.base.Page;
import com.demo.base.exception.DaoException;
import com.demo.sys.service.SysResourceService;

@RestController
@RequestMapping("sys/menu")
public class SysResourceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SysResourceService sysResourceService;

    @RequestMapping("list")
    @ResponseBody
    public R listJson(SysResource sysResource, Page page) {
        SysResourceExample example = new SysResourceExample();
        SysResourceExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        if (!StringUtils.isEmpty(sysResource.getResourceName())) {
            criteria.andResourceNameLike("%" + sysResource.getResourceName() + "%");
        }
        if (!StringUtils.isEmpty(sysResource.getResourceType())) {
            criteria.andResourceTypeLike("%" + sysResource.getResourceType() + "%");
        }
        if (!StringUtils.isEmpty(sysResource.getResourceUrl())) {
            criteria.andResourceUrlLike("%" + sysResource.getResourceUrl() + "%");
        }
        if (!StringUtils.isEmpty(page.getOrder()) && !StringUtils.isEmpty(page.getSortCol())) {
            example.setOrderByClause(CamelToUnderlineUtil.camelToUnderline(page.getSortCol()) + " " + page.getOrder());
        }
        example.setPage(page);
        try {
            List<SysResource> list = sysResourceService.selectByExample(example);
            Integer count = sysResourceService.countByExample(example);
            return R.ok().putObject("rows", list).putObject("total", count);
        } catch (DaoException e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping("getAllMenu")
    @ResponseBody
    public R getAllMenu(SysResource sysResource) {
        SysResourceExample example = new SysResourceExample();
        SysResourceExample.Criteria criteria = example.createCriteria();
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

    @RequestMapping("add")
    @Transactional
    public R addJson(SysResource sysResource) {
        Integer num = 0;
        try {
            num = sysResourceService.insertSelective(sysResource);
            if (num == 0) {
                return R.error("保存失败,无数据");
            }
        } catch (DaoException e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data", num);
    }

    @RequestMapping("get")
    public R get(Integer id) {
        try {
            return R.ok("data", sysResourceService.selectByPrimaryKey(id));
        } catch (DaoException e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping("edit")
    public R editJson(Map<String, Object> map, SysResource sysResource) {
        Integer num = 0;
        try {
            num = sysResourceService.updateByPrimaryKeySelective(sysResource);
            if (num == 0) {
                return R.error("保存失败,无数据");
            }
        } catch (DaoException e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data", num);
    }

    @RequestMapping("del")
    @ResponseBody
    public R delJson(Map<String, Object> map, @RequestParam("ids") List<Integer> ids) {
        Integer num = 0;
        try {
            for (int i = 0; i < ids.size(); i++) {
                num += sysResourceService.deleteByPrimaryKey(ids.get(i));
            }
        } catch (DaoException e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data", num);
    }
}