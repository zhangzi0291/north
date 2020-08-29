package com.north.base.startrunner;

import com.north.base.service.ShiroService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;

public class StartRunner implements ApplicationRunner {

    @Resource
    private ShiroService shiroService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        shiroService.updateFilterChains();
    }
}
