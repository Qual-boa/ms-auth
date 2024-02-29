package com.qualaboa.msauth.services;

public interface IService<T, ID> {
    T create(T t);
    T findById(ID id);
}
