package com.north.chat.controller;

import com.alibaba.fastjson.JSON;
import com.north.base.R;
import com.north.chat.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class ChatController {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    @SubscribeMapping("/subscribe")
    public R subscribe() {
        return R.ok("hello").putObject("content","收到订阅");
    }

    @MessageMapping("/hello")
    @SendTo("/chat/hello")
    public R greeting(ChatMessage message) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowStr = now .format(format);

        message.setDate(nowStr);
        System.out.println(JSON.toJSONString(message));
        return R.ok("hello").putObject("content",message);
    }

    @RequestMapping("sendMsg")
    @ResponseBody
    public R SendMessage(){
        return R.ok("ok");
    }

}
