package org.example.mastodon.connection.services.producers;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.mastodon.connection.model.events.MastodonPostEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.example.mastodon.connection.utils.Constant.TEST_MASTODON_POST_EVENT;
import static org.example.mastodon.connection.utils.Constant.TEST_POST_ID;
import static org.example.mastodon.connection.utils.Constant.TEST_TOPIC_NAME;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MastodonKafkaProducerTest {

    @Mock
    private KafkaTemplate<String, MastodonPostEvent> kafkaTemplate;

    @InjectMocks
    private MastodonKafkaProducer producer;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(producer, "mastodonTopicName", TEST_TOPIC_NAME);
    }

    @Test
    void testPublishingPostWhenGetValidEventThenSuccessfullyPublishThisEvent() {
        // given
        ProducerRecord<String, MastodonPostEvent> record =
                new ProducerRecord<>(TEST_TOPIC_NAME, TEST_POST_ID, TEST_MASTODON_POST_EVENT);
        SendResult<String, MastodonPostEvent> result = new SendResult<>(record, null);
        CompletableFuture<SendResult<String, MastodonPostEvent>> completableFuture =
                new CompletableFuture<>();
        completableFuture.complete(result);

        when(kafkaTemplate.send(eq(record))).thenReturn(completableFuture);

        // when
        producer.publishPost(TEST_MASTODON_POST_EVENT);

        // then
        verify(kafkaTemplate).send(eq(record));
    }

    @Test
    void testPublishingPostWhenEventIsNullThenDoNotSendMessage() {
        // when
        producer.publishPost(null);

        // then
        verify(kafkaTemplate, never()).send((Message<?>) any());
    }

    @Test
    void testPublishingPostWhenPayloadIsEmptyThenDoNotSendMessage() {
        // given
        MastodonPostEvent emptyPayloadEvent = new MastodonPostEvent(TEST_POST_ID, EMPTY);

        // when
        producer.publishPost(emptyPayloadEvent);

        // then
        verify(kafkaTemplate, never()).send((Message<?>) any());
    }

    @Test
    void testPublishingPostWhenSendFailsThenLogError() {
        // given
        ProducerRecord<String, MastodonPostEvent> record =
                new ProducerRecord<>(TEST_TOPIC_NAME, TEST_POST_ID, TEST_MASTODON_POST_EVENT);
        CompletableFuture<SendResult<String, MastodonPostEvent>> completableFuture =
                new CompletableFuture<>();
        completableFuture.completeExceptionally(new RuntimeException("Kafka send failed"));

        when(kafkaTemplate.send(eq(record))).thenReturn(completableFuture);

        // when
        assertDoesNotThrow(() -> producer.publishPost(TEST_MASTODON_POST_EVENT));

        // then
        verify(kafkaTemplate).send(eq(record));
    }
}