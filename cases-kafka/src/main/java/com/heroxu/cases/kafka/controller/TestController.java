package com.heroxu.cases.kafka.controller;

import com.heroxu.cases.kafka.proto.GrinMsgProto;
import com.heroxu.cases.kafka.stream.producer.ItemProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private ItemProducer itemProducer;

    @Autowired
    private KafkaTemplate kafkaTemplate;


    @GetMapping("/producer")
    public void testProducer() {
        itemProducer.product();
    }


    @GetMapping("/producer_grin")
    public void testProducerGrin() {
        GrinMsgProto.GrinMsg grinMsg= GrinMsgProto.GrinMsg.newBuilder().setBlockdiff(11).build();

        kafkaTemplate.send("grin_test",grinMsg);
    }
}
