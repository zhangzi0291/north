package com.north.base.service;

import java.io.Serializable;

/**
 * 接口的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/25 14:40
 */
public interface RedisService {

    void set(Serializable key, Object value);

    void set(Serializable key, Object value, Long expireTime);

    <T> T get(Serializable key,Class<T> clazz);
}
