package org.example.mastodon.connection.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.mastodon.connection.exceptions.MastodonMapperException;
import org.example.mastodon.connection.model.enteties.MastodonOffsetEntity;
import org.example.mastodon.connection.model.events.MastodonPostEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MastodonMapper {
    private final ObjectMapper objectMapper;

    public MastodonOffsetEntity convertJsonToOffsetEntity(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String payload = jsonNode.path("payload").asText();
            JsonNode payloadNode = objectMapper.readTree(payload);
            String postId = payloadNode.path("id").asText();

            return MastodonOffsetEntity.builder()
                    .postId(postId)
                    .payload(payload)
                    .build();
        } catch (JsonProcessingException e) {
            throw new MastodonMapperException(String.format("Failed convert json {%s} to offset entity", json), e);
        }
    }

    public MastodonPostEvent convertJsonToPostEvent(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String payload = jsonNode.path("payload").asText();
            JsonNode payloadNode = objectMapper.readTree(payload);
            String postId = payloadNode.path("id").asText();

            return MastodonPostEvent.builder()
                    .postId(postId)
                    .payload(payload)
                    .build();
        } catch (JsonProcessingException e) {
            throw new MastodonMapperException(String.format("Failed convert json {%s} to post event", json), e);
        }
    }
}
