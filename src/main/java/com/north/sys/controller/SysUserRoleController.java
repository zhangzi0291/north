package com.north.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.R;
import com.north.sys.entity.SysUserRole;
import com.north.sys.service.SysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("sysUserRole")
public class SysUserRoleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysUserRoleService sysUserRoleService;

    @RequestMapping("list")
    public R listJson(SysUserRole sysUserRole, Page page){
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();

        try {
            IPage<SysUserRole> pages = sysUserRoleService.selectByWrapperAndPage(page,wrapper);
            return R.ok().putObject("rows",pages.getRecords()).putObject("total", pages.getTotal());
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping("add")
    public R addJson(SysUserRole sysUserRole ){
    	Integer num = 0;
        try {
            num = sysUserRoleService.insert(sysUserRole);
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
            return R.ok("data",sysUserRoleService.selectById(id));
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping("edit")
    public R editJson(Map<String, Object> map, SysUserRole sysUserRole){
   		Integer num = 0;
        try {
            num = sysUserRoleService.updateById(sysUserRole);
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
            num = sysUserRoleService.deleteBatchIds(ids);
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
}