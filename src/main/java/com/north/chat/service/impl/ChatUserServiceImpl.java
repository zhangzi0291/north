package com.north.chat.service.impl;

import com.north.chat.entity.ChatUser;
import com.north.chat.mapper.ChatUserMapper;
import com.north.chat.service.IChatUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NorthZX
 * @since 2018-09-18
 */
@Service("IChatUserService")
public class ChatUserServiceImpl extends ServiceImpl<ChatUserMapper, ChatUser> implements IChatUserService {

}
