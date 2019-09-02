package com.heroxu.cases.kafka.proto;

import com.thinkerou.proto.helloworld.Callback;
import org.apache.kafka.common.serialization.Serializer;

public class GrinSerializer extends Adapter implements Serializer<Callback> {

    @Override
    public byte[] serialize(final String topic, final Callback data) {
        return data.toByteArray();
    }
}

