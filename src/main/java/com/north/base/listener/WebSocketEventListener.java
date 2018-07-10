package com.north.base.listener;

import com.alibaba.fastjson.JSONObject;
import com.north.base.service.RedisService;
import com.north.sys.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/7/10 13:26
 */
@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Resource
    private RedisService redisService;

    @EventListener
    public void handleConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headers.getSessionId();
        Principal user = headers.getUser();

        logger.info("[ws-connected] socket connect: {}", event.getMessage());
    }

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headers.getSessionId();
        Principal user = headers.getUser();

        logger.info("[ws-disconnect] socket disconnect: {}", event.getMessage());
    }


}
