package net.axel.sharehope.mapper;

public interface BaseMapper<E, Req , Res> {
    Res toResponse(E entity);
}
