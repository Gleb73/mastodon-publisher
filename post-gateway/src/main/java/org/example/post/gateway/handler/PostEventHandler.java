package org.example.post.gateway.handler;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.post.gateway.models.PostEvent;
import org.example.post.gateway.servers.PostServer;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
@KafkaListener(topics = "${spring.kafka.topic.mastodon-name}", groupId = "${spring.kafka.consumer.group-id}")
public class PostEventHandler {
    private final PostServer postServer;

    @KafkaHandler
    public void handler(PostEvent event) {
        log.info("receive event:{}", event.toString());
        postServer.sendPost(event);
    }
}
