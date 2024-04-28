package com.qualaboa.msauth.mappers;

public interface IMapper<T> {

    T toEntity(Object obj);
    Object toDto(T t);
}
