package com.north.base.tio.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.http.common.HttpRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.WsServerAioListener;

/**
 * 类的描述
 *
 * @Author zxn
 * @Date 2018-9-17 16:06
 */
public class NorthServerAioListener extends WsServerAioListener {

    private static Logger log = LoggerFactory.getLogger(NorthServerAioListener.class);
    private String groupId = "testgroup";
    public static final NorthServerAioListener me = new NorthServerAioListener();

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        super.onAfterConnected(channelContext, isConnected, isReconnect);
        if (log.isInfoEnabled()) {
            log.info("onAfterConnected\r\n{}", channelContext);
        }

    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
        super.onAfterDecoded(channelContext, packet, packetSize);
        if("403".equals(channelContext.getAttribute("error"))){
            WsResponse wsResponse = WsResponse.fromText("没有权限", "UTF-8");
            System.out.println("send error");
            Tio.send(channelContext,wsResponse);
        }

    }
}
