package com.artemnizhnyk.incognitochatapp.api.web.dto.mapper;

import com.artemnizhnyk.incognitochatapp.api.domain.model.Participant;
import com.artemnizhnyk.incognitochatapp.api.web.dto.ParticipantDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper extends Mappable<Participant, ParticipantDto> {
}
