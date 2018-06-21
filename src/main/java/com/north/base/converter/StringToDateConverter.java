package com.north.base.converter;


import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * String转Date转换器
 *
 * @Author zhengxiangnan
 * @Date 2018/3/26 14:39
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        return new Date(Long.valueOf(s));
    }
}
