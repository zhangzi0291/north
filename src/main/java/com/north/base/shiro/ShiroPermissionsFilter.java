package com.north.base.shiro;

import com.alibaba.fastjson.JSON;
import com.north.base.R;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/21 14:15
 */
public class ShiroPermissionsFilter extends PermissionsAuthorizationFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroPermissionsFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        logger.info("----------权限控制-------------");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String header = httpServletRequest.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest" == header;
        if (isAjax) {//如果是ajax返回指定格式数据
            logger.info("----------AJAX请求拒绝-------------");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            //返回禁止访问json字符串
            httpServletResponse.getWriter().write(JSON.toJSONString(R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"hh")));
        } else {//如果是普通请求进行重定向
            logger.info("----------普通请求拒绝-------------");
            httpServletResponse.sendRedirect("/403");
        }
        return false;
    }


}
