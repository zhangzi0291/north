package com.north.base.tio.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;

/**
 * 类的描述
 *
 * @Author zxn
 * @Date 2018-9-17 15:14
 */
public class NorthWsMsgHandler implements IWsMsgHandler {
    private static Logger log = LoggerFactory.getLogger(NorthWsMsgHandler.class);
    private String groupId = "testgroup";
    public static NorthWsMsgHandler me = new NorthWsMsgHandler();
    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     * 返回null断开连接
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientip = httpRequest.getClientIp();
        String token = httpRequest.getParam("token");
        if(StringUtils.isEmpty(token)){
            channelContext.setAttribute("error","403");
            return  httpResponse;
        }
        log.info("收到来自{}的ws握手包\r\n{}", clientip, httpRequest.toString());
        Tio.bindGroup(channelContext, groupId);
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String username = httpRequest.getParam("username");
        String password = httpRequest.getParam("password");
        log.debug("{}----{}",username,password);

        channelContext.setToken(username+password);
    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        String token = channelContext.getToken();
        log.debug("{}----{}",token);
        Tio.remove(channelContext, "receive close flag");
        return null;
    }

    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
        HttpRequest httpRequest = wsSessionContext.getHandshakeRequestPacket();//获取websocket握手包
        if (log.isDebugEnabled()) {
            log.debug("握手包:{}", httpRequest);
        }

        if (Objects.equals("heartbeat", text)) {
            return null;
        }

        log.info("收到ws消息:{}", text);
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(text, "UTF-8");

        Tio.sendToGroup(channelContext.getGroupContext(),groupId,wsResponse);
        return null;
    }
}
