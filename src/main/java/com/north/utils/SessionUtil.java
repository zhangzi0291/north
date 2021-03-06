package com.north.utils;

import com.north.base.service.RedisService;
import com.north.sys.entity.SysUser;
import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
