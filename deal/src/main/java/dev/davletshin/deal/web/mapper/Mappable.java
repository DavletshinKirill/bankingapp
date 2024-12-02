package dev.davletshin.deal.web.mapper;

import java.util.List;

public interface Mappable<E, D> {
    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDTO(List<E> entities);
}
