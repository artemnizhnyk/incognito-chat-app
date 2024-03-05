package com.artemnizhnyk.incognitochatapp.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ParticipantDto {

    private UUID id;
    private Instant enteredAt;
}
