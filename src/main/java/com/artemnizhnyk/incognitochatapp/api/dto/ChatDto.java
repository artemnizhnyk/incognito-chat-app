package com.artemnizhnyk.incognitochatapp.api.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    private UUID id;
    private String name;
    private Instant createdAt;
}
