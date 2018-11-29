package com.heroxu.cases.kafka.stream.topology;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class StreamStarter {


    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> kStream = builder.stream("x-items");

//        TumblingTopology tumblingTopology = new TumblingTopology();
//        tumblingTopology.buildTopology(kStream);


        Duration windowSizeMs = Duration.ofMinutes(1);

        kStream.filter((k, v) -> v != null)
                .map((k, v) -> {
                    JSONObject data = JSONObject.parseObject(v);
                    if (data == null) {
                        return new KeyValue<>(null, null);
                    } else {
                        return new KeyValue<>(data.getString("item_name"), data.getString("price"));
                    }
                })
                .filter((k, v) -> v != null)
                .groupByKey()
                .windowedBy(TimeWindows.of(windowSizeMs))
                .aggregate(String::new,
                        (aggKey, newValue, aggValue) -> {
                            JSONObject data = new JSONObject();
                            JSONObject aggJson = JSON.parseObject(aggValue);
                            Double aggPrice = aggJson.getDouble("price") == null ? 0d : aggJson.getDouble("price");
                            Double newPrice = 2000d;
                            try {
                                newPrice = Double.valueOf(newValue);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            data.put("price", aggPrice + newPrice);
                            data.put("item_name", aggKey);
                            return data.toJSONString();
                        })
                .toStream()
                .map((k, v) -> new KeyValue<>(k.key(), v))
                .to("item-sum-price", Produced.with(Serdes.String(), Serdes.String()));


        Topology topology=builder.build();
        System.out.println(topology.describe());
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
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
}
