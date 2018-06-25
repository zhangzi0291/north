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

    <T> T get(Serializable key,Class<T> clazz);
}
