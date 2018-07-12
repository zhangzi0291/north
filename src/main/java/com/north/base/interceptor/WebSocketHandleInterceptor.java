package com.north.base.interceptor;

import com.north.chat.entity.ChatUser;
import com.north.sys.entity.SysUser;
import com.north.sys.service.SysUserService;
import com.north.utils.EncryptionUtil;
import com.north.utils.SpringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/7/10 14:02
 */
public class WebSocketHandleInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            ChatUser unknowUser = new ChatUser();
            unknowUser.setId(-1);
            unknowUser.setName("未知用户");
            String username = accessor.getFirstNativeHeader("username");
            String password = accessor.getFirstNativeHeader("password");
            password = (EncryptionUtil.getPasswordEncoder(username,password));
            SysUser user = SpringUtil.getBean(SysUserService.class).findByName(username);
            if (StringUtils.isEmpty(user) || !user.getPassword().equals(password)) {
                accessor.setUser(unknowUser);
                return message;
            }

            if(user.getRoleList() == null || user.getRoleList().isEmpty() || user.getRoleList().stream().filter(u ->{
                if(u.getRoleName().equals("聊天") && !"0".equals(u.getStatus())){
                    return true;
                }
                return false;
            }).findFirst().orElse(null) == null){
                accessor.setUser(unknowUser);
                return message;
            }
            // 绑定user
            ChatUser chatUser = new ChatUser();
            BeanUtils.copyProperties(user,chatUser);
            accessor.setUser(chatUser);
        }
        return message;
    }

}
