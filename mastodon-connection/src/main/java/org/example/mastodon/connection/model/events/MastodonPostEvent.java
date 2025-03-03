package org.example.mastodon.connection.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MastodonPostEvent(
        @JsonProperty("postId")  String postId,
        @JsonProperty("payload") String payload
) { }
