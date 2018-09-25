package com.north.common.im;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 类的描述
 *
 * @Author zxn
 * @Date 2018-9-17 23:00
 */
@Configuration
public class ImConfig {

    public static Integer PORT;

    public static Boolean ENABLE;

    public static String NAME;

    @Value("${im.port:8081}")
    public void setPORT(Integer PORT) {
        ImConfig.PORT = PORT;
    }
    @Value("${im.enable:true}")
    public void setENABLE(Boolean ENABLE) {
        ImConfig.ENABLE = ENABLE;
    }
    @Value("${im.name:im}")
    public void setNAME(String NAME) {
        ImConfig.NAME = NAME;
    }
}
