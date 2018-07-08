package com.north.pub.controller;

import com.north.base.R;
import com.north.utils.IpUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {


    @RequestMapping("/getIp")
    public R getIp(HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        return R.ok().putObject("ip",ip);
    }

}
