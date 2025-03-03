package org.example.mastodon.connection.services;

import org.example.mastodon.connection.exceptions.MastodonResponseException;
import org.example.mastodon.connection.model.enteties.MastodonOffsetEntity;
import org.example.mastodon.connection.repositories.MastodonOffsetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.mastodon.connection.utils.Constant.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MastodonOffsetServiceImplTest {

    @Mock
    private MastodonOffsetRepository mastodonOffsetRepository;

    @InjectMocks
    private MastodonOffsetServiceImpl mastodonOffsetService;

    @Test
    void testSaveMethodWhenGetMastodonOffsetEntityThenSuccessfullySaveEntity() {
        // given
        MastodonOffsetEntity entity = MastodonOffsetEntity.builder()
                .id(TEST_ENTITY_ID)
                .postId(TEST_POST_ID)
                .payload(TEST_PAYLOAD)
                .build();
        when(mastodonOffsetRepository.save(eq(entity))).thenReturn(entity);

        // when
        mastodonOffsetService.save(entity);

        // then
        verify(mastodonOffsetRepository).save(eq(entity));
    }

    @Test
    void testSaveMethodWhenGetMastodonOffsetEntityThenThrowError() {
        // given
        MastodonOffsetEntity entity = MastodonOffsetEntity.builder()
                .id(TEST_ENTITY_ID)
                .postId(TEST_POST_ID)
                .payload(TEST_PAYLOAD)
                .build();
        when(mastodonOffsetRepository.save(eq(entity))).thenThrow(RuntimeException.class);

        // when & then
        Assertions.assertThrows(MastodonResponseException.class, () ->  mastodonOffsetService.save(entity));
        verify(mastodonOffsetRepository).save(eq(entity));
    }
}