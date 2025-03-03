package org.example.post.gateway.servers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.post.gateway.exceptions.PostMapperException;
import org.example.post.gateway.handler.WebSocketSessionHandler;
import org.example.post.gateway.models.PostEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostServer {
    private final WebSocketSessionHandler webSocketSessionHandler;
    private final ObjectMapper objectMapper;

    public void sendPost(PostEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            webSocketSessionHandler.sendMessageToAll(message);
        } catch (JsonProcessingException e) {
            throw new PostMapperException("Failed convert event to string message. Post id:%s".formatted(event.postId()), e);
        }
    }
}
