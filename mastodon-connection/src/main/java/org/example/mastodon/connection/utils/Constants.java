package org.example.mastodon.connection.utils;

import java.net.URI;

public final class Constants {

    private Constants() {
    }

    // Mastodon web socket constants
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String WEB_SOCKET_BASE_URL = "wss://mastodon.social/api/v1";
    public static final String STREAMING_PUBLIC_URL = "/streaming?stream=public";
    public static final String FULL_STREAMING_PUBLIC_URL = WEB_SOCKET_BASE_URL + STREAMING_PUBLIC_URL;
    public static final URI URI_ENTITY = URI.create(WEB_SOCKET_BASE_URL + STREAMING_PUBLIC_URL);

}
