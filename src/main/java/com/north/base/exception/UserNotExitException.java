package com.north.base.exception;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/7/10 16:41
 */
public class UserNotExitException extends Exception{

    public UserNotExitException() {
    }

    public UserNotExitException(String message) {
        super(message);
    }

    public UserNotExitException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExitException(Throwable cause) {
        super(cause);
    }

    public UserNotExitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
