package com.north.pub.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.R;
import com.north.sys.entity.SysUser;
import com.north.sys.service.SysUserService;
import com.north.utils.IpUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("public")
public class PublicController {

    @Resource
    private SysUserService userService;

    @RequestMapping("/getIp")
    public R getIp(HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        return R.ok().putObject("ip",ip);
    }

    @RequestMapping("/test")
    public R test(HttpServletRequest request) {
        Page page = new Page();
        IPage<SysUser> p =  userService.selectByWrapperAndPage(page,new QueryWrapper());
        return R.ok().putObject("ip",p).putObject("rows",p.getRecords());
    }

}
