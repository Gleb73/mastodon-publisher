package org.example.mastodon.connection.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.example.mastodon.connection.model.events.MastodonPostEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.retries}")
    private int retries;

    @Value("${spring.kafka.producer.properties.retry.backoff.ms}")
    private int retryBackoffMs;

    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    private boolean enableIdempotence;

    @Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection}")
    private int maxInFlightRequests;

    @Value("${spring.kafka.producer.batch-size}")
    private int batchSize;

    @Value("${spring.kafka.producer.linger-ms}")
    private int lingerMs;

    @Value("${spring.kafka.producer.buffer-memory}")
    private long bufferMemory;

    @Value("${spring.kafka.topic.mastodon-name:mastodon-posts-topic}")
    private String mastodonTopic;

    @Value("${spring.kafka.topic.partitions:3}")
    private int partitions;

    @Value("${spring.kafka.topic.replicas:2}")
    private int replicas;

    @Bean
    public ProducerFactory<String, MastodonPostEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        configProps.put(ACKS_CONFIG, acks);
        configProps.put(RETRIES_CONFIG, retries);
        configProps.put(RETRY_BACKOFF_MS_CONFIG, retryBackoffMs);
        configProps.put(ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        configProps.put(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequests);
        configProps.put(BATCH_SIZE_CONFIG, batchSize);
        configProps.put(LINGER_MS_CONFIG, lingerMs);
        configProps.put(BUFFER_MEMORY_CONFIG, bufferMemory);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, MastodonPostEvent> kafkaTemplate(ProducerFactory<String, MastodonPostEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic mastodonTopic() {
        return TopicBuilder.name(mastodonTopic)
                .partitions(partitions)
                .replicas(replicas)
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_COMPACT)
                .config(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, String.valueOf(replicas))
                .build();
    }
}
