package com.heroxu.cases.kafka.stream.topology;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;

import java.time.Duration;

public class TumblingTopology {


    public void buildTopology(KStream<String, String> source) {

        Duration windowSizeMs = Duration.ofMinutes(1);

        source.filter((k, v) -> v != null)
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
    }
}
