package com.heroxu.cases.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test", groupId = "myGroup")
    public void consume(String content) {
        System.out.println("myGroup message: " + content);
    }

    @KafkaListener(topics = "test", groupId = "myGroup2")
    public void consume2(String content) {
        System.out.println("myGroup2 message: " + content);
    }

    @KafkaListener(topics = "test", groupId = "myGroup3", containerFactory = "batchContainerFactory")
    public void consume3(List<String> content) {
        System.out.println("myGroup3 list->string message: " + content.stream().reduce((a, b) -> a + b).get());
    }
}
