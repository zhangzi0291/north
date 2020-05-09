package com.north.base.shiro;

import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class JWTFilter extends BearerHttpAuthenticationFilter {

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return super.isLoginAttempt(request, response);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        return super.executeLogin(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
            } catch (Exception e) {
//                String msg = e.getMessage();
//                Throwable throwable = e.getCause();
//                if (throwable != null && throwable instanceof SignatureVerificationException) {
//                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
//                } else if (throwable != null && throwable instanceof TokenExpiredException) {
//                    // AccessToken已过期
//                    if (this.refreshToken(request, response)) {
//                        return true;
//                    } else {
//                        msg = "Token已过期(" + throwable.getMessage() + ")";
//                    }
//                } else {
//                    if (throwable != null) {
//                        msg = throwable.getMessage();
//                    }
//                }
//                this.response401(request, response, msg);
                return false;
            }
        }
        return true;
    }
}
