package org.example.mastodon.connection.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.mastodon.connection.exceptions.MastodonResponseException;
import org.example.mastodon.connection.model.enteties.MastodonOffsetEntity;
import org.example.mastodon.connection.repositories.MastodonOffsetRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class MastodonOffsetServiceImpl implements MastodonOffsetService {
    private final MastodonOffsetRepository mastodonOffsetRepository;

    @Override
    public void save(MastodonOffsetEntity offset) {
        String postId = offset.getPostId();
        log.info("Starting to save MastodonOffsetEntity. Post id: {}", postId);

        try {
            mastodonOffsetRepository.save(offset);
            log.info("Successfully save offset to the database. Post id: {}", postId);
        } catch (Exception e) {
            log.error("Failed save offset to the database. Post id:{}, error:{}", postId, e.getMessage());
            throw new MastodonResponseException("Failed save offset", e);
        }
    }
}
