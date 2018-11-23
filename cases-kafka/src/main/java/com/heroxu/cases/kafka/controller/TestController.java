package com.heroxu.cases.kafka.controller;

import com.heroxu.cases.kafka.stream.producer.ItemProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private ItemProducer itemProducer;

    @GetMapping("/producer")
    public void testProducer() {
        itemProducer.product();
    }
}
