package com.north.pub.controller;

import com.north.base.R;
import com.north.base.service.ShiroService;
import com.north.base.shiro.ShiroRealm;
import com.north.sys.entity.SysLog;
import com.north.sys.service.SysUserService;
import com.north.utils.IpUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Resource
    private SysUserService userService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private ShiroService shiroService;

    @RequestMapping(path = "/getIp", method = {RequestMethod.GET, RequestMethod.POST})
    public R getIp(HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        return R.ok().putObject("ip",ip);
    }

    @RequestMapping(path = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public R test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SysLog sysLog = new SysLog();
        sysLog.setIp("127.0.0.1");
        sysLog.setCreateTime(LocalDateTime.now());
        redisTemplate.opsForValue().set("test",sysLog);
        return R.ok().putObject("test","ok");
    }

    @RequestMapping(path = "/test2", method = {RequestMethod.GET, RequestMethod.POST})
    public R test2() {
        List<Object> json = new ArrayList<>();
        shiroService.updateFilterChains();
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
