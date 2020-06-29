package com.north.base.shiro;

import com.github.streamone.shiro.session.RedissonSession;
import com.north.base.data.RedisProperties;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SimpleSession;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
public class RedisSessionFactory implements SessionFactory {

    @Resource
    private RedisProperties redisProperties;

    public static final String SESSION_INFO_KEY_PREFIX = "session:info:";
    public static final String SESSION_ATTR_KEY_PREFIX = "session:attr:";

    public RedisSessionFactory() {
    }

    public Session createSession(SessionContext initData) {

        if (initData != null) {
            String sessionId = initData.getHost();
            System.out.println();
            String host = initData.getHost();
            if (host != null) {
                return new RedissonSession(getRedissonClient(),new JsonJacksonCodec(),
                        SESSION_INFO_KEY_PREFIX+sessionId,SESSION_ATTR_KEY_PREFIX+sessionId,sessionId);
            }
        }

        return new SimpleSession();
    }

    private RedissonClient getRedissonClient(){
        RedissonClient redisson;
        Config config = new Config();
        String url = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
        if(!StringUtils.isEmpty(redisProperties.getPassword())){
            config.useSingleServer().setAddress(url)
                    .setPassword(redisProperties.getPassword())
                    .setDatabase(redisProperties.getDatabase());
        }else {
            config.useSingleServer().setAddress(url)
                    .setDatabase(redisProperties.getDatabase());
        }

        try {
            redisson = Redisson.create(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("RedissonClient init redis url:["+url+"], Exception:");
        }
        return redisson;
    }
}
