package com.artemnizhnyk.incognitochatapp.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class MessageDto {

    private String from;
    private String message;
    @Builder.Default
    private Instant createdAt = Instant.now();
}
