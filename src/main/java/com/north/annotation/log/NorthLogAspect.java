package com.north.annotation.log;

import com.north.sys.entity.SysLog;
import com.north.sys.entity.SysUser;
import com.north.sys.service.ISysLogService;
import com.north.utils.IpUtil;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class NorthLogAspect {

    @Resource
    private ISysLogService sysLogService;

    @Pointcut(value = "@annotation(com.north.annotation.log.NorthLog)")
    public void access() {

    }

    @After("access()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        NorthLog northLog = AnnotationUtils.findAnnotation(signature.getMethod(),NorthLog.class);
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        SysLog sysLog = new SysLog();
        sysLog.setIp(IpUtil.getIpAddr(request));

        sysLog.setModuleName(joinPoint.getSignature().getDeclaringTypeName());
        sysLog.setOperationName(joinPoint.getSignature().getName());
        SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
        String username = StringUtils.isEmpty(sysUser.getName())?sysUser.getUsername():sysUser.getName();
        sysLog.setUsername(username);

        if(!StringUtils.isEmpty(northLog.remark())) {
            sysLog.setRemark(northLog.remark());
        }
        sysLog.setCreateTime(LocalDateTime.now());

        sysLogService.save(sysLog);

    }

}
