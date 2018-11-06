package com.north.chat.service.impl;

import com.north.chat.entity.ChatApp;
import com.north.chat.mapper.ChatAppMapper;
import com.north.chat.service.IChatAppService;
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
@Service("IChatAppService")
public class ChatAppServiceImpl extends ServiceImpl<ChatAppMapper, ChatApp> implements IChatAppService {

}
