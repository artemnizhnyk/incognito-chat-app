package com.artemnizhnyk.incognitochatapp.api.service;

import com.artemnizhnyk.incognitochatapp.api.domain.model.Participant;
import com.artemnizhnyk.incognitochatapp.api.web.dto.ParticipantDto;
import com.artemnizhnyk.incognitochatapp.api.web.websocket.ParticipantWsController;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void handleJoinChat(final String sessionId, final UUID participantId, final String chatId) {
        Participant participant = Participant.builder()
                .sessionId(sessionId)
                .id(participantId)
                .chatId(chatId)
                .build();

        messagingTemplate.convertAndSend(
                ParticipantWsController.getFetchParticipantJoinInChatDestination(chatId),
                ParticipantDto.builder()
                        .id(participant.getId())
                        .enteredAt(participant.getEnteredAt())
                        .build()
        );
    }

    @Override
    public void handleUnsubscribe(final SessionUnsubscribeEvent event) {

    }

    @Override
    public void handleDisconnect(final SessionDisconnectEvent event) {

    }

    @Override
    public void handleLeaveChat(final AbstractSubProtocolEvent event) {

    }

    @Override
    public Stream<Participant> getParticipants(final String chatId) {
        return null;
    }

    private static class ParticipantKeyHelper {

        private static final String KEY = "artem:nizhnyk:incognito-chat:chats:{chat_id}:participants";

        public static String makeKey(String chatId) {
            return KEY.replace("{chat_id}", chatId);
        }
    }
}
