package org.example.post.gateway.exceptions;

public class DeserializerException extends RuntimeException {
    public DeserializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
