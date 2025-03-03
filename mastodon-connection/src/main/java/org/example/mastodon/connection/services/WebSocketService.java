package org.example.mastodon.connection.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.mastodon.connection.model.enteties.MastodonOffsetEntity;
import org.example.mastodon.connection.model.events.MastodonPostEvent;
import org.example.mastodon.connection.services.producers.MastodonKafkaProducer;
import org.example.mastodon.connection.utils.MastodonMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import static org.example.mastodon.connection.utils.Constants.URI_ENTITY;

@Service
@AllArgsConstructor
@Log4j2
public class WebSocketService {
    private final WebSocketClient webSocketClient;
    private final MastodonOffsetService mastodonOffsetService;
    private final MastodonMapper mapper;
    private final MastodonKafkaProducer producer;

    public void connect() {
        webSocketClient.execute(URI_ENTITY, this::handleMessage)
                .doOnError(error -> log.error("WebSocket connection error: {}", error.getMessage()))
                .retry()
                .subscribe();
    }

    private Mono<Void> handleMessage(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .filter(message -> message.contains("\"event\":\"update\""))
                .flatMap(this::processMessage)
                .then();
    }

    private Mono<Void> processMessage(String message) {
        try {
            log.info("Fetching message:{}", message);

            MastodonPostEvent postEvent = mapper.convertJsonToPostEvent(message);
            producer.publishPost(postEvent);

            MastodonOffsetEntity offset = mapper.convertJsonToOffsetEntity(message);
            mastodonOffsetService.save(offset);

            log.info("Published to Kafka: {}", postEvent.postId());
        } catch (Exception e) {
            log.error("Failed to process WebSocket message: {}, type:{}, stacktrace:{}", e.getMessage(), e.getClass(), e.getStackTrace());
        }
        return Mono.empty();
    }
}
