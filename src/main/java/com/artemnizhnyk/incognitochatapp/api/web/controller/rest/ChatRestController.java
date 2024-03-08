package com.artemnizhnyk.incognitochatapp.api.web.controller.rest;

import com.artemnizhnyk.incognitochatapp.api.service.ChatService;
import com.artemnizhnyk.incognitochatapp.api.web.dto.ChatDto;
import com.artemnizhnyk.incognitochatapp.api.web.dto.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ChatRestController {

    private final ChatService chatService;
    private final ChatMapper chatMapper;

    public static final String FETCH_CHATS = "/api/chats";

    @GetMapping(value = FETCH_CHATS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ChatDto> fetchChats() {
        return chatService
                .getChats()
                .map(chatMapper::toDto)
                .collect(Collectors.toList());
    }
}
