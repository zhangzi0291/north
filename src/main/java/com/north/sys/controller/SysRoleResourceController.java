package com.north.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.R;
import com.north.sys.entity.SysRoleResource;
import com.north.sys.service.SysRoleResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("sysRoleResource")
public class SysRoleResourceController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysRoleResourceService sysRoleResourceService;
    
    @RequestMapping(path = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public R listJson(SysRoleResource sysRoleResource, Page page){
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        try {
            IPage<SysRoleResource> pages = sysRoleResourceService.page(page,wrapper);
            return R.ok().putObject("rows",pages.getRecords()).putObject("total", pages.getTotal());
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
    
    @RequestMapping(path = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public R addJson(SysRoleResource sysRoleResource ){
    	Boolean flag = false;
        try {
            flag = sysRoleResourceService.save(sysRoleResource);
            if(!flag){
                return R.error("保存失败,无数据");
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",flag);
    }
    
   	@RequestMapping(path = "get", method = {RequestMethod.GET, RequestMethod.POST})
    public R get(String id){
        try {
            return R.ok("data",sysRoleResourceService.getById(id));
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
   
    @RequestMapping(path = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public R editJson(Map<String, Object> map, SysRoleResource sysRoleResource){
   		Boolean flag = false;
        try {
            flag = sysRoleResourceService.updateById(sysRoleResource);
            if(!flag){
                return R.error("保存失败,无数据");
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",flag);
    }
    
    @RequestMapping(path = "del", method = {RequestMethod.GET, RequestMethod.POST})
    public R delJson(Map<String, Object> map, @RequestParam("ids") List<Integer> ids ){
        Boolean flag = false;
        try {
            flag = sysRoleResourceService.removeByIds(ids);
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",flag);
    }
}