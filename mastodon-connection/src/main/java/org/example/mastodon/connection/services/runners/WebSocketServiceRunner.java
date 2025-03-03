package org.example.mastodon.connection.services.runners;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.mastodon.connection.services.WebSocketService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class WebSocketServiceRunner {
    private final WebSocketService webSocketService;

    @EventListener(ApplicationReadyEvent.class)
    public void startWebSocketClient() {
        log.info("Start to connect web socket");
        webSocketService.connect();
    }
}
