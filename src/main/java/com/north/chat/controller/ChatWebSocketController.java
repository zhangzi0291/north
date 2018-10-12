package com.north.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatWebSocketController {

//    @Resource
//    private SimpMessagingTemplate messagingTemplate;
//
//    @SubscribeMapping("/subscribe")
//    public R subscribe(@Headers Map<String, Object> headers, Principal principal) throws UserNotExitException{
//        ChatUser user = (ChatUser) principal;
//        if(user.isEmpty() || user.getId().equals("-1")) {
//           throw new UserNotExitException("用户不存在");
//        }
//        return R.ok("userinfo").putObject("name",user.getName()).putObject("uid",user.getId()).putObject("imgSrc",user.getImgSrc());
//    }
//
//    @MessageMapping("/msg")
//    @SendTo("/chat/msg")
//    public R greeting(@Headers Map<String, Object> headers, ChatMessage message) throws Exception {
//
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String nowStr = now.format(format);
//        message.setDate(nowStr);
//        System.out.println(JSON.toJSONString(message));
//        return R.ok().putObject("content", message);
//    }
//
//    @SendTo("/sys/msg")
//    public R sysMsg(String message){
//        return R.ok().putObject("content",message);
//    }
//
//    @MessageExceptionHandler(UserNotExitException.class)
//    public R handleException(UserNotExitException exception) {
//        String message = StringUtils.isEmpty(exception.getMessage())?exception.toString():exception.getMessage();
//        messagingTemplate.convertAndSend("/sys/msg", R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),message));
//        return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"Exception: " + exception.toString());
//    }
//
//
//    @RequestMapping("sendMsg")
//    @ResponseBody
//    public R SendMessage(){
//        return R.ok("ok");
//    }

}
