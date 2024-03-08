package com.artemnizhnyk.incognitochatapp.api.web.controller.rest;

import com.artemnizhnyk.incognitochatapp.api.service.ParticipantService;
import com.artemnizhnyk.incognitochatapp.api.web.dto.ParticipantDto;
import com.artemnizhnyk.incognitochatapp.api.web.dto.mapper.ParticipantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ParticipantRestController {

    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;

    public static final String FETCH_PARTICIPANTS = "/api/chats/{chat_id}/participants";

    @GetMapping(value = FETCH_PARTICIPANTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParticipantDto> fetchParticipants(@PathVariable("chat_id") String chatId) {
        return participantService
                .getParticipants(chatId)
                .map(participantMapper::toDto)
                .collect(Collectors.toList());
    }
}
