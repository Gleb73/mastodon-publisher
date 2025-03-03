package org.example.mastodon.connection.exceptions;

public class MastodonMapperException extends RuntimeException {
    public MastodonMapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
