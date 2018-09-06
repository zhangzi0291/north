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
    
    @RequestMapping("list")
    public R listJson(SysRoleResource sysRoleResource, Page page){
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        try {
            IPage<SysRoleResource> pages = sysRoleResourceService.selectByWrapperAndPage(page,wrapper);
            return R.ok().putObject("rows",pages.getRecords()).putObject("total", pages.getTotal());
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
    
    @RequestMapping("add")
    public R addJson(SysRoleResource sysRoleResource ){
    	Integer num = 0;
        try {
            num = sysRoleResourceService.insert(sysRoleResource);
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
    public R get(Integer id){
        try {
            return R.ok("data",sysRoleResourceService.selectById(id));
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
   
    @RequestMapping("edit")
    public R editJson(Map<String, Object> map, SysRoleResource sysRoleResource){
   		Integer num = 0;
        try {
            num = sysRoleResourceService.updateById(sysRoleResource);
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
    
    public R delJson(Map<String, Object> map, @RequestParam("ids") List<Integer> ids ){
        Integer num = 0;
        try {
            num = sysRoleResourceService.deleteBatchIds(ids);
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
}