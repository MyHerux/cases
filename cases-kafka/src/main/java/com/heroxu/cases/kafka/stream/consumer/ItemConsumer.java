package com.heroxu.cases.kafka.stream.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ItemConsumer {

    @KafkaListener(topics = "tumbling-item-sum-price", groupId = "myGroup4")
    public void tumblingConsume(@Payload String price,
                             @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        System.out.println("tumblingConsume --> " + "|key: " + key + "|price: " + price);
    }

    @KafkaListener(topics = "hopping-item-sum-price", groupId = "myGroup4")
    public void hoppingConsume(@Payload String price,
                             @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        System.out.println("hoppingConsume --> " + "|key: " + key + "|price: " + price);
    }

    @KafkaListener(topics = "final-item-sum-price", groupId = "myGroup4")
    public void finalConsume(@Payload String price,
                             @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        System.out.println("finalConsume --> " + "|key: " + key + "|price: " + price);
    }
}
