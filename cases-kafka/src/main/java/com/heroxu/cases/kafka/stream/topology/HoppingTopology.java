package com.heroxu.cases.kafka.stream.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;

public class HoppingTopology extends AbstractTopology {

    void buildTopology(KStream<String, String> source) {

        // 窗口间隔
        Duration windowSizeMs = Duration.ofMinutes(5);
        Duration advanceWindowSizeMs = Duration.ofMinutes(1);

        // 窗口数据存储
        String storeName = "hopping_item_store";
        Materialized<String, String, WindowStore<Bytes, byte[]>> materialized =
                Materialized.<String, String, WindowStore<Bytes, byte[]>>as(storeName).withValueSerde(Serdes.String());

        // Topology
        source.filter((k, v) -> v != null)
                .map(keyValueMapper)
                .filter((k, v) -> v != null)
                .groupByKey()
                .windowedBy(TimeWindows.of(windowSizeMs).advanceBy(advanceWindowSizeMs))
                .aggregate(initializer, aggregator, materialized)
                .toStream()
                .map((k, v) -> new KeyValue<>(k.key(), v))
                .to("hopping-item-sum-price", Produced.with(Serdes.String(), Serdes.String()));
    }
}
