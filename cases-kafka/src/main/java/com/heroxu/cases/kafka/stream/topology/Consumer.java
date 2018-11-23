package com.heroxu.cases.kafka.stream.topology;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class Consumer {

    public static void main(String[] args) {
        consumeOutput("localhost:9092");
    }

    private static void consumeOutput(String bootstrapServers) {
        final String resultTopic = "streams-output";
        final Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,
                "pageview-region-lambda-example-consumer");
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (final KafkaConsumer<String, Long> consumer = new KafkaConsumer<>(consumerProperties)) {
            consumer.subscribe(Collections.singleton(resultTopic));
            while (true) {
                final ConsumerRecords<String, Long> consumerRecords = consumer.poll(Long.MAX_VALUE);
                for (final ConsumerRecord<String, Long> consumerRecord : consumerRecords) {
                    String tx = consumerRecord.key().replace("[", "").replace("]", "");
                    String[] idTime = tx.split("@");
                    String id = idTime[0];
                    String[] time = idTime[1].split("/");
                    String endTime = time[1];
                    long endTimesLong = Long.valueOf(endTime);
                    long now = new Date().getTime() / 1000 * 1000;
                    String date = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
                    System.out.println(date + " --> " + endTimesLong / 1000 + " ---> " + ((now / 1000)) + " --> " + consumerRecord.value());
                    if (id.equals("1") && getBet(endTimesLong / 1000, now / 1000)) {
                        System.out.println("------------------->" + date + " --> " + endTimesLong / 1000 + " ---> " + ((now / 1000)) + " --> " + consumerRecord.value());
                    }
                }
            }
        }
    }


    static boolean getBet(long endTimestemp, long now) {
        return now - 10 < endTimestemp && endTimestemp < now + 10;
    }
}
