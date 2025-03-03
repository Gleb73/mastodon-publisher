package org.example.mastodon.connection.services.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.mastodon.connection.model.events.MastodonPostEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static io.micrometer.common.util.StringUtils.*;
import static java.util.Objects.isNull;

@Component
@Log4j2
@RequiredArgsConstructor
public class MastodonKafkaProducer {
    private final KafkaTemplate<String, MastodonPostEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.mastodon-name:mastodon-posts-topic}")
    private String mastodonTopicName;

    public void publishPost(final MastodonPostEvent event) {
        if (isNull(event) || isBlank(event.payload())) {
            return;
        }

        log.info("Starting publish event with post id:{}", event.postId());

        ProducerRecord<String, MastodonPostEvent> record =
                new ProducerRecord<>(mastodonTopicName, event.postId(), event);
        CompletableFuture<SendResult<String, MastodonPostEvent>> future = kafkaTemplate.send(record);

        log.info("Published event with post id:{}", event.postId());
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to send message: {}", exception.getMessage());
            } else {
                log.info("Message sent successfully: {}", result.getRecordMetadata());
            }
        });
    }
}
