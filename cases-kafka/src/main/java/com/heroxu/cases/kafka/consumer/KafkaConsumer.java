package com.heroxu.cases.kafka.consumer;

import com.heroxu.cases.kafka.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
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


    @KafkaListener(topics = "streams-output", groupId = "myGroup2")
    public void consume4(ConsumerRecords<String, String> content) {
        for (ConsumerRecord consumerRecord : content) {
            String tx = consumerRecord.key().toString().replace("[", "").replace("]", "");
            String[] idTime = tx.split("@");
            String id = idTime[0];
            String[] time = idTime[1].split("/");
            String startTime = time[0];
            String endTime = time[1];
            long endTimestemp = Long.valueOf(endTime);
            long now = new Date().getTime();

            if (id.equals("1") && endTimestemp == now) {
                System.out.println("---" + consumerRecord.value() + "     " + tx);
            }
        }
    }


    @KafkaListener(topics = "streams-output", groupId = "myGroup3")
    public void consume6(@Payload String foo,
                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key) {
        System.out.println("myGroup2 message: " + key + foo);
    }



    public static void main(String[] args) {
        String tx = "[1@1542867480000/1542867780000]";
        tx = tx.replace("[", "").replace("]", "");
        String[] idTime = tx.split("@");
        String id = idTime[0];
        String[] time = idTime[1].split("/");
        String startTime = time[0];
        String endTime = time[1];
        System.out.println("start: " + startTime + "  end: " + endTime);
    }
}
