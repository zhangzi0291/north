package com.north.sys.controller;

import com.north.base.Page;
import com.north.base.R;
import com.north.sys.entity.SysUser;
import com.north.sys.entity.SysUserDto;
import com.north.sys.entity.SysUserExample;
import com.north.sys.service.SysUserRoleService;
import com.north.sys.service.SysUserService;
import com.north.utils.CamelToUnderlineUtil;
import com.north.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/user")
public class SysUserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @RequestMapping("list")
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
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }
    
    @RequestMapping("add")
    public R addJson(SysUser sysUser ,Integer roleId){
    	Integer num = 0;
    	if(!StringUtils.isEmpty(sysUser.getPassword())){
            sysUser.setPassword(EncryptionUtil.getPasswordEncoder(sysUser.getUsername(),sysUser.getPassword()));
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
        } catch (Exception e) {
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
            sysUser.setPassword(EncryptionUtil.getPasswordEncoder(sysUser.getUsername(),sysUser.getPassword()));
        }
        try {
            num = sysUserService.updateByPrimaryKeySelective(sysUser);
            sysUserRoleService.updateUserRole(sysUser.getId(),roleId);
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
                num+=sysUserService.deleteByPrimaryKey(ids.get(i));
                sysUserRoleService.deleteUserRole(ids.get(i));
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",num);
    }
}