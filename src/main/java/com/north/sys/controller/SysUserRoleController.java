package com.north.sys.controller;

import com.north.base.Page;
import com.north.base.R;
import com.north.sys.entity.SysUserRole;
import com.north.sys.entity.SysUserRoleExample;
import com.north.sys.service.SysUserRoleService;
import com.north.utils.CamelToUnderlineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        //设置查询条件 。。。
        example.setPage(page);
        if(!StringUtils.isEmpty(page.getOrder())&&!StringUtils.isEmpty(page.getSortCol())){
            example.setOrderByClause(CamelToUnderlineUtil.camelToUnderline(page.getSortCol())+" "+page.getOrder());
        }
        try {
            List< SysUserRole> list = sysUserRoleService.selectByExample(example);
            Integer count = sysUserRoleService.countByExample(example);
            return R.ok().putObject("rows",list).putObject("total", count);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
    
    @RequestMapping("add")
    @Transactional
    public R addJson(SysUserRole sysUserRole ){
    	Integer num = 0;
        try {
            num = sysUserRoleService.insertSelective(sysUserRole);
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
            return R.ok("data",sysUserRoleService.selectByPrimaryKey(id));
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
   
    @RequestMapping("edit")
    public R editJson(Map<String, Object> map, SysUserRole sysUserRole){
   		Integer num = 0;
        try {
            num = sysUserRoleService.updateByPrimaryKeySelective(sysUserRole);
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
            for(int i=0;i<ids.size();i++){
                num+=sysUserRoleService.deleteByPrimaryKey(ids.get(i));
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
}