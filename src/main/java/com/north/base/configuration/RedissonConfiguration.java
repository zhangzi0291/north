package com.north.base.configuration;


import com.north.base.data.RedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@ConditionalOnProperty(name = "north.shiro-filter.enable-redis",havingValue = "true")
@Configuration
public class RedissonConfiguration {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient(){
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
