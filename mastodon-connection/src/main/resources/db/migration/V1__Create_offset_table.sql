create table if not exists mastodon_offset (
    id BIGSERIAL PRIMARY KEY,
    post_id VARCHAR(255) NOT NULL UNIQUE,
    payload VARCHAR(20000) NOT NULL
    );