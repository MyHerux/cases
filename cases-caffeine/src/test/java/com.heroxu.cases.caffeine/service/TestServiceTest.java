package com.heroxu.cases.caffeine.service;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceTest {

    @Resource
    private TestService testService;

    @Test
    public void TestA(){
        testService.testA();
    }
}
