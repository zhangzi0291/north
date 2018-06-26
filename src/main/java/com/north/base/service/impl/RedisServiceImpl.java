package com.north.base.service.impl;

import com.north.base.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/25 14:41
 */
@Service("RedisService")
public class RedisServiceImpl implements RedisService{

    @Resource
    private RedisTemplate<Serializable,Object> redisTemplate;

    @Override
    public void set(Serializable key, Object value) {
        ValueOperations<Serializable,Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    @Override
    public void set(Serializable key, Object value, Long expireTime) {
        ValueOperations<Serializable,Object> vo = redisTemplate.opsForValue();
        vo.set(key, value, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> T get(Serializable key, Class<T> clazz) {
        ValueOperations<Serializable,Object> vo = redisTemplate.opsForValue();
        return clazz.cast(vo.get(key));
    }
}
