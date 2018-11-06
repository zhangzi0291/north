package com.north.chat.service.impl;

import com.north.chat.entity.ChatMsg;
import com.north.chat.mapper.ChatMsgMapper;
import com.north.chat.service.IChatMsgService;
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
@Service("IChatMsgService")
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper, ChatMsg> implements IChatMsgService {

}
