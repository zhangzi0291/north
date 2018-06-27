package com.north.base.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/26 11:07
 */
@Component
public class SimpleSessionFactory implements SessionFactory{

    @Override
    public Session createSession(SessionContext sessionContext) {
        if (sessionContext != null) {
            String host = sessionContext.getHost();
            if (host != null) {
                return new SimpleSession(host);
            }
        }
        return new SimpleSession();
    }

}
