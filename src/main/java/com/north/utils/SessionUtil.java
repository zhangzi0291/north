package com.north.utils;

import com.demo.base.security.user.WebSecurityCustomUserDetal;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/3/7 15:08
 */
public class SessionUtil {

    public static WebSecurityCustomUserDetal getCurrentUserDetal(){
        return (WebSecurityCustomUserDetal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
