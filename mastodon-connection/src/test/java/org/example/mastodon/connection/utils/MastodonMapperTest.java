package org.example.mastodon.connection.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mastodon.connection.exceptions.MastodonMapperException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.mastodon.connection.utils.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MastodonMapperTest {
    private ObjectMapper objectMapper;
    private MastodonMapper mapper;

    @BeforeEach
    public void setup() {
        objectMapper = spy(ObjectMapper.class);
        mapper = new MastodonMapper(objectMapper);
    }

    @Test
    void testConvertJsonToOffsetEntityWhenGetValidJsonThenReturnEntity() throws JsonProcessingException {
        // when
        var actualEntity = mapper.convertJsonToOffsetEntity(TEST_JSON);

        // then
        assertNotNull(actualEntity);
        assertEquals(TEST_MASTODON_OFFSET_ENTITY.getPostId(), actualEntity.getPostId());
        assertEquals(TEST_MASTODON_OFFSET_ENTITY.getPayload(), actualEntity.getPayload());
        verify(objectMapper).readTree(eq(TEST_JSON));
        verify(objectMapper).readTree(eq(TEST_PAYLOAD));
    }

    @Test
    void testConvertJsonToPostEventWhenGetValidJsonThenReturnPostEvent() throws JsonProcessingException {
       // when
        var actualEntity = mapper.convertJsonToPostEvent(TEST_JSON);

        // then
        assertNotNull(actualEntity);
        assertEquals(TEST_MASTODON_POST_EVENT, actualEntity);
        verify(objectMapper).readTree(eq(TEST_JSON));
        verify(objectMapper).readTree(eq(TEST_PAYLOAD));
    }

    @Test
    void testConvertJsonToOffsetEntityWhenGetValidJsonThenThrowError() throws JsonProcessingException {
        // given
        when(objectMapper.readTree(TEST_JSON)).thenThrow(JsonProcessingException.class);

        // when & then
        assertThrows(MastodonMapperException.class, () -> mapper.convertJsonToOffsetEntity(TEST_JSON));
    }

    @Test
    void testConvertJsonToPostEventWhenGetValidJsonThenThrowError() throws JsonProcessingException {
        // given
        when(objectMapper.readTree(TEST_JSON)).thenThrow(JsonProcessingException.class);

        // when & then
        assertThrows(MastodonMapperException.class, () -> mapper.convertJsonToPostEvent(TEST_JSON));
    }
}