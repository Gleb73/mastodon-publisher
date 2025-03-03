package org.example.mastodon.connection.model.enteties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "mastodon_offset")
@Getter
@AllArgsConstructor
@Builder
public class MastodonOffsetEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "post_id", nullable = false, unique = true)
        private String postId;

        @Column(name = "payload", nullable = false)
        private String payload;

        public MastodonOffsetEntity() {
        }
}
