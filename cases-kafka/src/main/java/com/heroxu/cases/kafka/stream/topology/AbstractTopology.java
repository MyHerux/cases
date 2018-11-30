package com.heroxu.cases.kafka.stream.topology;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.KeyValueMapper;

import java.math.BigDecimal;

public abstract class AbstractTopology
{
    // 转换数据 kv
    KeyValueMapper<String, String, KeyValue<String, String>> keyValueMapper = (k, v) -> {
        JSONObject data = JSONObject.parseObject(v);
        if (data == null) {
            return new KeyValue<>(null, null);
        } else {
            return new KeyValue<>(data.getString("itemName"), data.getString("price"));
        }
    };

    // 设定初始数据
    Initializer<String> initializer = () -> "0";

    // 聚合数据
    Aggregator<String, String, String> aggregator = (aggKey, newValue, aggValue) -> {
        BigDecimal b1 = new BigDecimal(aggValue);
        BigDecimal b2 = new BigDecimal(newValue);
        return b1.add(b2).toString();
    };

}
