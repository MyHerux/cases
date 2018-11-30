package com.heroxu.cases.kafka.stream.topology;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;

import static org.apache.kafka.streams.kstream.Suppressed.BufferConfig.unbounded;
import static org.apache.kafka.streams.kstream.Suppressed.untilWindowCloses;

public class FinalTopology extends AbstractTopology {

    void buildTopology(KStream<String, String> source) {

        // 窗口间隔
        Duration windowSizeMs = Duration.ofMinutes(5);
        Duration advanceWindowSizeMs = Duration.ofMinutes(1);

        // 窗口延迟
        Duration graceSizeMs = Duration.ofSeconds(5);

        // 窗口数据存储
        String storeName = "final_item_store";
        Materialized<String, String, WindowStore<Bytes, byte[]>> materialized =
                Materialized.<String, String, WindowStore<Bytes, byte[]>>as(storeName).withValueSerde(Serdes.String());

        Serde<Windowed<String>> windowedSerde = Serdes.serdeFrom(new TimeWindowedSerializer<>(new StringSerializer()), new TimeWindowedDeserializer<>(new StringDeserializer()));

        // Topology
        source.filter((k, v) -> v != null)
                .map(keyValueMapper)
                .filter((k, v) -> v != null)
                .groupBy((k, v) -> k, Serialized.with(Serdes.String(), Serdes.String()))
                .windowedBy(TimeWindows.of(windowSizeMs).advanceBy(advanceWindowSizeMs).grace(graceSizeMs))
                .aggregate(initializer, aggregator, materialized)
                .suppress(untilWindowCloses(unbounded()))
                .toStream()
                .map((k, v) -> new KeyValue<>(k.key(), v))
                .to("final-item-sum-price", Produced.with(Serdes.String(), Serdes.String()));
    }
}
