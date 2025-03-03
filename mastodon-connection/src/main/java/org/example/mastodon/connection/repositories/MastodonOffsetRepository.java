package org.example.mastodon.connection.repositories;

import org.example.mastodon.connection.model.enteties.MastodonOffsetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MastodonOffsetRepository extends JpaRepository<MastodonOffsetEntity, Long> {
}
