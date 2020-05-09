package com.north.pub.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.north.base.R;
import com.north.sys.service.SysUserService;
import com.north.test.service.TestExcelService;
import com.north.utils.IpUtil;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Resource
    private SysUserService userService;
    @Resource
    private TestExcelService testExcelService;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    @Qualifier("shiroFilter")
    private ShiroFilterFactoryBean shiroFilter;

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
    @RequestMapping(path = "/test2", method = {RequestMethod.GET, RequestMethod.POST})
    public R test2() {
        JSONArray json = new JSONArray();
        shiroFilter.getFilterChainDefinitionMap().forEach((String key,String value) ->{
            json.add(key+"======"+value);
            System.out.println(key+"======"+value);
        });
        return R.ok().putObject("test2",json);
    }

    @RequestMapping(path = "/excelTest", method = {RequestMethod.GET, RequestMethod.POST})
    public R excelTest(HttpServletRequest request) throws Exception{

        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        req.getFileMap().forEach((String fileName,MultipartFile file)->{
            try {
                List<String> cellName = Arrays.asList(new String[] {"null","name","mobile","username","email"});

                testExcelService.importExcel(file.getInputStream(),cellName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        return R.ok().putObject("test","ok");
    }

    public static void main(String[] args) {
//        String jsonStr = "{timestamp : \"2020-04-28 17:49:31.724\", app : \"zuulserver\", thread : \"http-nio-8041-exec-2\", logger: \"com.cx.zuulserver.auth.JwtLoginFilter\", level: \"INFO\", msg: \"token有效期1000L * 60 * 60 * 24 * 365 * 501576800000000\"}";
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        System.out.println(jsonObject.toJSONString());
//        System.out.println(JSON.toJSONString(jsonObject.toJSONString()).substring(1,JSON.toJSONString(jsonObject.toJSONString()).length()-1));
        System.out.println(new Date().getTime());
    }
}
