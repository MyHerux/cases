package com.heroxu.cases.kafka.stream.topology;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;


import java.util.Properties;
import java.util.concurrent.CountDownLatch;


public class WordCountApplication {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
        // Note: To re-run the demo, you need to use the offset reset tool:
        // https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Streams+Application+Reset+Tool
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source = builder.stream("RDS");
        String storeName = "rds-store";
        Serde<Windowed<String>> windowedSerde = Serdes.serdeFrom(new TimeWindowedSerializer(new StringSerializer()), new TimeWindowedDeserializer(new StringDeserializer()));

        covertToWindowCount(source);

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }


    // Tumbling time windows
    private static void covertToWindowCount(KStream<String, String> source) {
        long windowSizeMs = 5 * 60 * 1000;
        long advanceSizeMs = 60 * 1000;
        KTable<Windowed<String>, Long> kTable = source.filter((k, v) -> v != null)
                .map((k, v) -> {
                    JSONObject data = null;
                    try {
                        data = JSONObject.parseObject(v).getJSONArray("Data").getJSONObject(0);
                    } catch (Exception e) {
                        System.out.println("----: " + v);
                    }

                    if (data == null) {
                        return new KeyValue<>(null, null);
                    } else {
                        return new KeyValue<>(data.getString("workerOid"), data.toJSONString());
                    }
                })
                .filter((k, v) -> v != null)
                .groupByKey()
                .windowedBy(TimeWindows.of(windowSizeMs))
                .count();

        kTable.mapValues(Object::toString).toStream((k, v) -> {
            System.out.println(k + v);
            return k.toString();
        }).to("streams-output", Produced.with(Serdes.String(), Serdes.String()));
    }

    // Hopping time windows
    private static void covertToWindowCount2(KStream<String, String> source) {
        long windowSizeMs = 5 * 60 * 1000;
        long advanceSizeMs = 60 * 1000;
        KTable<Windowed<String>, Long> kTable = source.filter((k, v) -> v != null)
                .map((k, v) -> {
                    JSONObject data = null;
                    try {
                        data = JSONObject.parseObject(v).getJSONArray("Data").getJSONObject(0);
                    } catch (Exception e) {
                        System.out.println("----: " + v);
                    }

                    if (data == null) {
                        return new KeyValue<>(null, null);
                    } else {
                        return new KeyValue<>(data.getString("workerOid"), data.toJSONString());
                    }
                })
                .filter((k, v) -> v != null)
                .groupByKey()
                .windowedBy(TimeWindows.of(windowSizeMs).advanceBy(advanceSizeMs))
                .count();

        kTable.mapValues(Object::toString).toStream((k, v) -> {
            System.out.println(k + v);
            return k.toString();
        }).to("streams-output", Produced.with(Serdes.String(), Serdes.String()));
    }


}