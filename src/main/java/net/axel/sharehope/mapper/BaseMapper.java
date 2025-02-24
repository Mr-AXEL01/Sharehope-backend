package net.axel.sharehope.mapper;

public interface BaseMapper<E, Req , Res> {

    E toEntity(Req dto);

    Res toResponse(E entity);
}
