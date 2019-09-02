package com.heroxu.cases.kafka.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrinDeserializer
    extends Adapter implements Deserializer<GrinMsgProto.GrinMsg> {


    @Override
    public GrinMsgProto.GrinMsg deserialize(final String topic, byte[] data) {
        try {
            return GrinMsgProto.GrinMsg.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {

            return null;

        }
    }
}
