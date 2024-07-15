package org.mizuro.aviatickets.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.mizuro.aviatickets.models.MailMessage;

import java.io.IOException;

public class MessageSerializer implements Serializer<MailMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, MailMessage message) {
        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
