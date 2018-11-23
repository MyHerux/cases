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
        List<JSONObject> orders = readOrtder();
        List<JSONObject> users = readUser();
        items.forEach(item -> kafkaTemplate.send(new ProducerRecord<String, String>("items", item.getString("item_name"), item.toJSONString())));
        orders.forEach(order -> kafkaTemplate.send(new ProducerRecord<String, String>("orders", order.getString("user_name"), order.toJSONString())));
        users.forEach(user -> kafkaTemplate.send(new ProducerRecord<String, String>("users", user.getString("name"), user.toJSONString())));

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


    private static List<JSONObject> readOrtder() {
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
                    data.put("user_name", values[0]);
                    data.put("item_name", values[1]);
                    data.put("transaction_date", values[2]);
                    data.put("quantity", values[3]);
                    return data;
                })
                .collect(Collectors.toList());
    }



    private static List<JSONObject> readUser() {
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
                    data.put("name", values[0]);
                    data.put("address", values[1]);
                    data.put("gender", values[2]);
                    data.put("age", values[3]);
                    return data;
                })
                .collect(Collectors.toList());
    }


}
