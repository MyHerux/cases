package com.heroxu.cases.kafka.consumer;

import com.heroxu.cases.kafka.proto.GrinMsgProto;
import com.thinkerou.proto.helloworld.Callback;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
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


    @KafkaListener(topics = "streams-output", groupId = "myGroup3")
    public void consume6(@Payload String foo,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        System.out.println("myGroup2 message: " + key + foo);
    }

    @KafkaListener(topics = {"grin_test"})
    public void listen(ConsumerRecord<?, GrinMsgProto.GrinMsg> record) {
        GrinMsgProto.GrinMsg grinMsg = record.value();
        System.out.println("message: {}" + grinMsg.toString());
    }
}
