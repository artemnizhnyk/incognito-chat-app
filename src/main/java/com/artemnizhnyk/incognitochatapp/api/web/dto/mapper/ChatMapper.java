package com.artemnizhnyk.incognitochatapp.api.web.dto.mapper;

import com.artemnizhnyk.incognitochatapp.api.domain.model.Chat;
import com.artemnizhnyk.incognitochatapp.api.web.dto.ChatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper extends Mappable<Chat, ChatDto> {
}
