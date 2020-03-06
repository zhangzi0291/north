package com.north.pub.controller;

import com.north.base.R;
import com.north.sys.service.SysUserService;
import com.north.utils.IpUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("public")
public class PublicController {

    @Resource
    private SysUserService userService;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(path = "/getIp", method = {RequestMethod.GET, RequestMethod.POST})
    public R getIp(HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        return R.ok().putObject("ip",ip);
    }

    @RequestMapping(path = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public R test(String topic) {
        for(int i=0;i<10;i++) {
            ListenableFuture<SendResult<String, String>> value = kafkaTemplate.send(topic,"key", "hello world "+i);
        }
        return R.ok().putObject("test","ok");
    }
}
