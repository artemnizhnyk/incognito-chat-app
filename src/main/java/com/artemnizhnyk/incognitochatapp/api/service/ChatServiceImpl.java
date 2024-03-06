package com.artemnizhnyk.incognitochatapp.api.service;

import com.artemnizhnyk.incognitochatapp.api.domain.model.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {


    @Override
    public void createChat(final String chatName) {

    }

    @Override
    public void deleteChat(final UUID id) {

    }

    @Override
    public Stream<Chat> getChats() {
        return null;
    }
}
