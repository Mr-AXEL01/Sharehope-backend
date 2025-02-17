package net.axel.sharehope.mapper;

public interface BaseMapper<E, Req> {

    E toEntity(Req dto);
}
