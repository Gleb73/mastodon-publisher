package org.example.mastodon.connection.exceptions;

public class MastodonResponseException extends RuntimeException {
    public MastodonResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
