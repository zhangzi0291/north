package com.north.base.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/26 11:07
 */
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
