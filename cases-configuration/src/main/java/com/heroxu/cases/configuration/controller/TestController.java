package com.heroxu.cases.configuration.controller;

import com.heroxu.cases.configuration.config.Test4Config;
import com.heroxu.cases.configuration.config.Test5Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private Test4Config test4Config;

    @GetMapping(value = "/test4_config")
    public void Test4Config(){
        System.out.println("test4 -> | testName -> " + test4Config.getName() + " | test id -> " + test4Config.getId());
    }

    @GetMapping(value = "/test5_config")
    public void Test5ConfigX(){
        System.out.println("test5 -> | testName -> " + Test5Config.name + " | test id -> " + Test5Config.id);
    }
}
