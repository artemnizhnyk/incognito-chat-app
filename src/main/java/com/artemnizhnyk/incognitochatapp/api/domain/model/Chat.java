package com.artemnizhnyk.incognitochatapp.api.domain.model;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Chat implements Serializable {

    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    @Builder.Default
    private Instant createdAt = Instant.now();
}
