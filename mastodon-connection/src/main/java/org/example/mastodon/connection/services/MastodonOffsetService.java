package org.example.mastodon.connection.services;

import org.example.mastodon.connection.model.enteties.MastodonOffsetEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MastodonOffsetService {

    void save(MastodonOffsetEntity offset);
}
