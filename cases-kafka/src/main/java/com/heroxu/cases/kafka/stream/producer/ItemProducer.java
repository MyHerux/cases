package com.heroxu.cases.kafka.stream.producer;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;


    public void product() {
        List<JSONObject> items = readItem();
        items.forEach(item -> kafkaTemplate.send(new ProducerRecord<String, String>("x-items", item.getString("item_name"), item.toJSONString())));
    }

    private static List<JSONObject> readItem() {
        String[] baseData = new String[]{"iphone, BJ, phone, 5388.88",
                "ipad, SH, pad, 4888.88",
                "iwatch, SZ, watch, 2668.88",
                "ipod, GZ, pod, 1888.88"};
        List<String> lines = Arrays.asList(baseData);
        return lines.stream()
                .filter(StringUtils::isNoneBlank)
                .map((String line) -> line.split("\\s*,\\s*"))
                .filter((String[] values) -> values.length == 4)
                .map((String[] values) -> {
                    JSONObject data = new JSONObject();
                    data.put("item_name", values[0]);
                    data.put("address", values[1]);
                    data.put("type", values[2]);
                    data.put("price", values[3]);
                    return data;
                })
                .collect(Collectors.toList());
    }


}
