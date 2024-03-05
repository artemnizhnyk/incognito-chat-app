package com.artemnizhnyk.incognitochatapp.api.domain;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Participant implements Serializable {

    private UUID id;
    @Builder.Default
    private Instant enteredAt = Instant.now();
    private String sessionId;
    private String chatId;
}
