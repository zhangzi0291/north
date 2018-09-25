package com.north.utils;

import com.alibaba.fastjson.JSON;
import com.north.base.service.RedisService;
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

    public String setToken(SysUser user){
        String token = Base64.getEncoder().encodeToString(JSON.toJSONBytes(user));
        redisService.set(user.getUsername(),token);
        return token;
    }

    public String getToken(String username){
        if(StringUtils.isEmpty(username)){
            return "";
        }
        String base64 = redisService.get(username,String.class);
        return base64;
    }

    public SysUser getTokenUser(String token){
        if(StringUtils.isEmpty(token)){
            return new SysUser();
        }
        SysUser user = JSON.parseObject(Base64.getDecoder().decode(token),SysUser.class);
        return user;
    }
}
