package org.example.post.gateway.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.post.gateway.exceptions.DeserializerException;
import org.example.post.gateway.models.PostEvent;

import java.util.Map;

public class CustomPostEventJsonDeserializer implements Deserializer<PostEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public PostEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, PostEvent.class);
        } catch (Exception e) {
            throw new DeserializerException("Error deserializing PostEvent", e);
        }
    }

    @Override
    public void close() {
    }
}
