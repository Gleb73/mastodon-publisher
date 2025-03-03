package org.example.post.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostEvent(
        @JsonProperty("postId") String postId,
        @JsonProperty("payload") String payload
) { }
