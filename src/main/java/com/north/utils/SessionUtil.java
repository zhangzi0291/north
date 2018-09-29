package com.north.utils;

import com.alibaba.fastjson.JSON;
import com.north.base.service.RedisService;
import com.north.base.session.SimpleSession;
import com.north.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Base64;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/3/7 15:08
 */
@Component
public class SessionUtil {

    @Value("${north.token-key}")
    private String tokenKey;
    @Resource
    private RedisService redisService;

    public SimpleSession getSession(String sessionId){
        if(StringUtils.isEmpty(sessionId)){
            return new SimpleSession();
        }
        SimpleSession simpleSession = redisService.get(sessionId,SimpleSession.class);
        return simpleSession;
    }

    public SysUser getSessionUser(String sessionId){
        if(StringUtils.isEmpty(sessionId)){
            return new SysUser();
        }
        SysUser user = (SysUser)getSession(sessionId).getAttribute("user");
        return user;
    }
}
