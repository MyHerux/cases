package com.heroxu.cases.caffeine.controller;

import com.heroxu.cases.caffeine.service.TestService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping(value = "/test_a")
    public int getTestA(){
        return testService.testA();
    }

    @GetMapping(value = "/test_b")
    public int getTestB(){
        return testService.testB();
    }

    @GetMapping(value = "/test_c")
    public String getTestC(){
        return testService.testC();
    }
}
