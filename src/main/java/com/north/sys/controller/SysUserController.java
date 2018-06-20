package com.north.sys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.demo.base.R;
import com.demo.sys.entity.SysUserDto;
import com.demo.sys.service.SysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.demo.utils.CamelToUnderlineUtil;

import com.demo.base.Page;
import com.demo.base.exception.DaoException;
import com.demo.sys.entity.SysUser;
import com.demo.sys.entity.SysUserExample;
import com.demo.sys.service.SysUserService;

@RestController
@RequestMapping("sys/user")
public class SysUserController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @RequestMapping("list")
    @ResponseBody
    public R listJson(SysUser sysUser, Page page){
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        //设置查询条件 。。。
        example.setPage(page);
        if(!StringUtils.isEmpty(page.getOrder())&&!StringUtils.isEmpty(page.getSortCol())){
            example.setOrderByClause(CamelToUnderlineUtil.camelToUnderline(page.getSortCol())+" "+page.getOrder());
        }
        try {
            List< SysUser> list = sysUserService.selectByExample(example);
            Integer count = sysUserService.countByExample(example);
            return R.ok().putObject("rows",list).putObject("total", count);
        } catch (DaoException e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
    
    @RequestMapping("add")
    @Transactional
    public R addJson(SysUser sysUser ,Integer roleId){
    	Integer num = 0;
    	if(!StringUtils.isEmpty(sysUser.getPassword())){
            sysUser.setPassword(new ShaPasswordEncoder().encodePassword(sysUser.getPassword(),null));
        }
    	if(roleId == null){
            return R.error("保存失败,必须选择角色");
        }
        try {
    	    sysUser.setCreateTime(new Date());
            num = sysUserService.insertSelective(sysUser);
            sysUserRoleService.insertUserRole(sysUser.getId(),roleId);
            if(num==0){
                return R.error("保存失败,无数据");
            }
        } catch (DaoException e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
    
   	@RequestMapping("get")
    public R get(Integer id){
        try {
            SysUserDto user =  sysUserService.getUserAndRoleId(id);
            user.setPassword(null);
            return R.ok("data",user);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
   
    @RequestMapping("edit")
    public R editJson(Map<String, Object> map, SysUser sysUser, Integer roleId){
   		Integer num = 0;
        if(!StringUtils.isEmpty(sysUser.getPassword())){
            sysUser.setPassword(new ShaPasswordEncoder().encodePassword(sysUser.getPassword(),null));
        }
        try {
            num = sysUserService.updateByPrimaryKeySelective(sysUser);
            sysUserRoleService.updateUserRole(sysUser.getId(),roleId);
            if(num==0){
                return R.error("保存失败,无数据");
            }
        } catch (DaoException e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
    
    @RequestMapping("del")
    @ResponseBody
    public R delJson(Map<String, Object> map, @RequestParam("ids") List<Integer> ids ){
        Integer num = 0;
        try {
            for(int i=0;i<ids.size();i++){
                num+=sysUserService.deleteByPrimaryKey(ids.get(i));
                sysUserRoleService.deleteUserRole(ids.get(i));
            }
        } catch (DaoException e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
}