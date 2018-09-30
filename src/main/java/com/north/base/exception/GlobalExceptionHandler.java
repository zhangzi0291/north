package com.north.base.exception;

import com.north.base.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/9/30 9:28
 */
@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static final String DEFALT_ERROR_VIEW = "error";

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public R businessExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        logger.error("Exception:",e);
        response.setStatus(500);
        return R.error("服务器异常");
    }


}
