package com.north.base.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{



//    @Bean
//    public WsServerStarter northWebsocketStarter() throws IOException {
//        if(ImConfig.ENABLE) {
//            Integer port = ImConfig.PORT;
//
//            WsServerStarter wsServerStarter = new WsServerStarter(port, NorthWsMsgHandler.me);
//            ServerGroupContext serverGroupContext = wsServerStarter.getServerGroupContext();
//            serverGroupContext.setName(ImConfig.NAME);
//            serverGroupContext.setServerAioListener(NorthServerAioListener.me);
//            //设置ip监控
//            serverGroupContext.setIpStatListener(NorthIpStatListener.me);
//            //设置ip统计时间段
//            serverGroupContext.ipStats.addDurations(new Long[]{Time.MINUTE_1});
//            //设置心跳超时时间
//            serverGroupContext.setHeartbeatTimeout(1000 * 60);
//
//            wsServerStarter.start();
//            return wsServerStarter;
//        }
//        return null;
//    }

//    private static long HEART_BEAT=10000;
//    @Resource
//    private SessionUtil sessionUtil;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//
//        ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
//        te.setPoolSize(1);
//        te.setThreadNamePrefix("wss-heartbeat-thread-");
//        te.initialize();
//        // 订阅Broker名称
//        registry.enableSimpleBroker("/sys","/chat","/user").setHeartbeatValue(new long[]{HEART_BEAT,HEART_BEAT}).setTaskScheduler(te);
//        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
//        registry.setApplicationDestinationPrefixes("/app");
//        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
//        // registry.setUserDestinationPrefix("/user/");
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(webSocketHandleInterceptor());
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/websocket")
////                .addInterceptors(new HandshakeInterceptor() {
////                    /**
////                     * websocket握手
////                     */
////                    @Override
////                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
////                        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
////                        //获取token认证
////                        String username = req.getServletRequest().getParameter("username");
////                        String password = req.getServletRequest().getParameter("password");
////                        String token = req.getServletRequest().getHeader("username");
////                        //解析token获取用户信息
////                        ChatUser chatUser = new ChatUser();
////                        BeanUtils.copyProperties(sessionUtil.getTokenUser(token),chatUser);
////                        Principal user = chatUser.getId()==null?null:chatUser;
////                        if(user == null){   //如果token认证失败user为null，返回false拒绝握手
////                            return true;
////                        }
////                        //保存认证用户
////                        attributes.put("user", user);
////                        return true;
////                    }
////
////                    @Override
////                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
////
////                    }
////                })
////                    .setHandshakeHandler(new DefaultHandshakeHandler(){
////                    @Override
////                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
////                        //设置认证用户
////                        return (Principal)attributes.get("user");
////                    }
////                })
//                .setAllowedOrigins("*")
//                .withSockJS();
//    }
//
//    @Bean
//    public WebSocketHandleInterceptor webSocketHandleInterceptor(){
//        return new WebSocketHandleInterceptor();
//    }
}
