package com.artemnizhnyk.incognitochatapp.api.web.dto.mapper;

import java.util.List;

public interface Mappable<E, D> {

    E toEntity(final D dto);

    D toDto(final E entity);

    List<E> toEntity(final List<D> dto);

    List<D> toDto(final List<E> entity);
}
