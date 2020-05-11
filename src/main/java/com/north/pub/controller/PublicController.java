package com.north.pub.controller;

import com.alibaba.fastjson.JSONArray;
import com.north.base.R;
import com.north.sys.service.SysUserService;
import com.north.utils.IpUtil;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("public")
public class PublicController {

    @Resource
    private SysUserService userService;
    @Autowired
    @Qualifier("shiroFilter")
    private ShiroFilterFactoryBean shiroFilter;

    @RequestMapping(path = "/getIp", method = {RequestMethod.GET, RequestMethod.POST})
    public R getIp(HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        return R.ok().putObject("ip",ip);
    }


    @RequestMapping(path = "/test2", method = {RequestMethod.GET, RequestMethod.POST})
    public R test2() {
        JSONArray json = new JSONArray();
        shiroFilter.getFilterChainDefinitionMap().forEach((String key,String value) ->{
            json.add(key+"======"+value);
            System.out.println(key+"======"+value);
        });
        return R.ok().putObject("test2",json);
    }


    public static void main(String[] args) {
//        String jsonStr = "{timestamp : \"2020-04-28 17:49:31.724\", app : \"zuulserver\", thread : \"http-nio-8041-exec-2\", logger: \"com.cx.zuulserver.auth.JwtLoginFilter\", level: \"INFO\", msg: \"token有效期1000L * 60 * 60 * 24 * 365 * 501576800000000\"}";
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        System.out.println(jsonObject.toJSONString());
//        System.out.println(JSON.toJSONString(jsonObject.toJSONString()).substring(1,JSON.toJSONString(jsonObject.toJSONString()).length()-1));
        System.out.println(new Date().getTime());
    }
}
