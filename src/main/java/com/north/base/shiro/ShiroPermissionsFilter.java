package com.north.base.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/21 14:15
 */
public class ShiroPermissionsFilter extends FormAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(ShiroPermissionsFilter.class);

    public final String SAVED_REQUEST_KEY = "shiroSavedRequest";

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        redirectToLogin(request, response);
    }

    @Override
    protected void saveRequest(ServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        javax.servlet.http.HttpServletRequest httpRequest = WebUtils.toHttp(request);
        SavedRequest savedRequest = new SavedRequest(httpRequest);
        session.setAttribute(SAVED_REQUEST_KEY, savedRequest);
    }

//    @Override
//    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
//        logger.info("----------权限控制-------------");
//        ShiroHttpServletRequest httpServletRequest = (ShiroHttpServletRequest) servletRequest;
//        ShiroHttpServletResponse httpServletResponse = (ShiroHttpServletResponse) servletResponse;
//        String header = httpServletRequest.getHeader("X-Requested-With");
//        boolean isAjax = "XMLHttpRequest" == header;
//        if (isAjax) {//如果是ajax返回指定格式数据
//            logger.info("----------AJAX请求拒绝-------------");
//            httpServletResponse.setCharacterEncoding("UTF-8");
//            httpServletResponse.setContentType("application/json");
//            //返回禁止访问json字符串
//            httpServletResponse.getWriter().write(JSON.toJSONString(R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"hh")));
//        } else {//如果是普通请求进行重定向
//            logger.info("----------普通请求拒绝-------------");
//            httpServletResponse.sendRedirect("/sys/403");
//        }
//        return false;
//    }


}
