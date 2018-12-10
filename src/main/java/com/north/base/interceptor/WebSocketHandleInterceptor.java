package com.north.base.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/7/10 14:02
 */
public class WebSocketHandleInterceptor implements ChannelInterceptor {

    @Value("${north.token-key}")
    private String tokenKey;

//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String username = accessor.getFirstNativeHeader("username");
//            String password = accessor.getFirstNativeHeader("password");
//            String token = accessor.getFirstNativeHeader(tokenKey);
//            if(!StringUtils.isEmpty(token)){
//                SysUser user = SpringUtil.getBean(SessionUtil.class).getSessionUser(token);
//                // 绑定user
//                ChatUser chatUser = new ChatUser();
//                BeanUtils.copyProperties(user, chatUser);
////                accessor.setUser(chatUser);
//            }else {
//                password = (EncryptionUtil.getPasswordEncoder(username, password));
//                SysUser user = SpringUtil.getBean(SysUserService.class).findByName(username);
//
//                Assert.isTrue(!StringUtils.isEmpty(user) && user.getPassword().equals(password),"登陆失败");
//
//                Assert.isTrue(user.getRoleList() != null && user.getRoleList().isEmpty() || user.getRoleList().stream().filter(u -> {
//                    if (u.getRoleName().equals("聊天") && !"0".equals(u.getStatus())) {
//                        return false;
//                    }
//                    return true;
//                }).findFirst().orElse(null) == null,"没有权限");
//                // 绑定user
//                ChatUser chatUser = new ChatUser();
//                BeanUtils.copyProperties(user, chatUser);
////                accessor.setUser(chatUser);
//            }
//        }
//        return message;
//    }

}
