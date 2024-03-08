package com.artemnizhnyk.incognitochatapp.api.service;

import com.artemnizhnyk.incognitochatapp.api.domain.model.Participant;
import com.artemnizhnyk.incognitochatapp.api.web.dto.ParticipantDto;
import com.artemnizhnyk.incognitochatapp.api.web.dto.mapper.ParticipantMapper;
import com.artemnizhnyk.incognitochatapp.api.web.controller.websocket.ParticipantWsController;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor
@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ChatService chatService;
    private final ParticipantMapper participantMapper;

    private final SimpMessagingTemplate messagingTemplate;
    private final SetOperations<String, Participant> setOperations;
    private static final Map<String, Participant> sessionIdToParticipantMap = new ConcurrentHashMap<>();

    @Override
    public void handleJoinChat(final String sessionId, final UUID participantId, final String chatId) {
        log.info(String.format("Participant \"%s\" join in chat \"%s\".", sessionId, chatId));

        Participant participant = Participant.builder()
                .sessionId(sessionId)
                .id(participantId)
                .chatId(chatId)
                .build();

        sessionIdToParticipantMap.put(participant.getSessionId(), participant);

        setOperations.add(ParticipantKeyHelper.makeKey(chatId), participant);

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
        handleLeaveChat(event);
    }

    @Override
    public void handleDisconnect(final SessionDisconnectEvent event) {
        handleLeaveChat(event);
    }

    @Override
    public void handleLeaveChat(final AbstractSubProtocolEvent event) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());

        Optional
                .ofNullable(headerAccessor.getSessionId())
                .map(sessionIdToParticipantMap::remove)
                .ifPresent(participant -> {

                    String chatId = participant.getChatId();

                    log.info(
                            String.format(
                                    "Participant \"%s\" leave from \"%s\" chat.",
                                    participant.getSessionId(),
                                    chatId
                            )
                    );

                    String key = ParticipantKeyHelper.makeKey(chatId);

                    setOperations.remove(key, participant);

                    Optional
                            .ofNullable(setOperations.size(key))
                            .filter(size -> size == 0L)
                            .ifPresent(size -> chatService.deleteChat(UUID.fromString(chatId)));

                    messagingTemplate.convertAndSend(
                            key,
                            participantMapper.toDto(participant)
                    );
                });
    }

    @Override
    public Stream<Participant> getParticipants(final String chatId) {
        return Optional
                .ofNullable(setOperations.members(ParticipantKeyHelper.makeKey(chatId)))
                .orElseGet(HashSet::new)
                .stream();
    }

    private static class ParticipantKeyHelper {

        private static final String KEY = "artem:nizhnyk:incognito-chat:chats:{chat_id}:participants";

        public static String makeKey(String chatId) {
            return KEY.replace("{chat_id}", chatId);
        }
    }
}
