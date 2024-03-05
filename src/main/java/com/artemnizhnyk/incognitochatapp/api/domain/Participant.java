package com.artemnizhnyk.incognitochatapp.api.domain;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Participant implements Serializable {

    @Builder.Default
    private Instant enteredAt = Instant.now();
    private String sessionId;
    private String participantId;
    private String chatId;
}
