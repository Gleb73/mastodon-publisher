package org.example.mastodon.connection.utils;

import org.example.mastodon.connection.model.enteties.MastodonOffsetEntity;
import org.example.mastodon.connection.model.events.MastodonPostEvent;

import java.util.UUID;

public final class Constant {

    private Constant() {
    }

    public static final long TEST_ENTITY_ID = UUID.randomUUID().getLeastSignificantBits();
    public static final String TEST_POST_ID = "114095886177660048";
    public static final String TEST_PAYLOAD = """
     {"id":"%s","sensitive":false}""".formatted(TEST_POST_ID);

    public static final String TEST_JSON = """
            {
              "stream" : ["public"],
              "event" : "update",
              "payload" : "{\\"id\\":\\"%s\\",\\"sensitive\\":false}"
            }
            """.formatted(TEST_POST_ID);

    public static final String INVALID_MESSAGE = "{\"event\":\"delete\",\"postId\":\"123\"}";

    public static final MastodonPostEvent TEST_MASTODON_POST_EVENT = MastodonPostEvent.builder()
            .postId(TEST_POST_ID)
            .payload(TEST_PAYLOAD)
            .build();
    public static final MastodonOffsetEntity TEST_MASTODON_OFFSET_ENTITY = MastodonOffsetEntity.builder()
            .id(TEST_ENTITY_ID)
            .postId(TEST_POST_ID)
            .payload(TEST_PAYLOAD)
            .build();

    public static final String TEST_TOPIC_NAME = "test_topic_name";
}
