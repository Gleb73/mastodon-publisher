package org.example.mastodon.connection.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.example.mastodon.connection.utils.Constants.*;

@Configuration
@Log4j2
public class WebSocketConfig {

    @Value("${mastodon.api.access-token}")
    private String accessToken;

    @Value("${mastodon.retry.response-timeout-ms}")
    private int responseTimoutMs;

    @Value("${mastodon.retry.connection-timeout-ms}")
    private int connectionTimeoutMs;

    @Value("${mastodon.retry.enable-log}")
    private boolean isLogEnable;

    @Value("${mastodon.socket.max-connections}")
    private int maxConnections;

    @Value("${mastodon.socket.duration-pending-ms}")
    private int durationPendingMs;

    @Value("${mastodon.socket.max-idle-time-ms}")
    private int maxIdleTimeMs;

    @Bean
    public ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("fixed")
                .maxConnections(maxConnections)
                .pendingAcquireTimeout(Duration.ofMillis(durationPendingMs))
                .maxIdleTime(Duration.ofMillis(maxIdleTimeMs))
                .build();
    }

    @Bean
    public HttpClient httpClient() {
        log.info("Create http client");
        return HttpClient.create(connectionProvider())
                .followRedirect(true)
                .baseUrl(FULL_STREAMING_PUBLIC_URL)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutMs)
                .responseTimeout(Duration.ofMillis(responseTimoutMs))
                .headers(headers -> headers.add(AUTH_HEADER, BEARER + accessToken))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(connectionTimeoutMs, TimeUnit.MILLISECONDS)))
                .wiretap(isLogEnable);
    }

    @Bean
    public WebSocketClient webSocketClient() {
        log.info("initialization ReactorNettyWebSocketClient");
        return new ReactorNettyWebSocketClient(httpClient());
    }
}
