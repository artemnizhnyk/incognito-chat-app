package com.artemnizhnyk.incognitochatapp.api.web.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ChatDto {

    private UUID id;
    private String name;
    private Instant createdAt;
}
