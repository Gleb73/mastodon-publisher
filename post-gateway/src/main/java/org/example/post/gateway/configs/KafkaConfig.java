package org.example.post.gateway.configs;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.post.gateway.deserializers.CustomPostEventJsonDeserializer;
import org.example.post.gateway.models.PostEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedEventPackages;

    @Bean
    public ConsumerFactory<String, PostEvent> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        configProps.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(TRUSTED_PACKAGES, trustedEventPackages);

        return new DefaultKafkaConsumerFactory<>(configProps,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new CustomPostEventJsonDeserializer()));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, PostEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, PostEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
