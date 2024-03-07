package com.qualaboa.msauth.services;

import java.util.List;

public interface IService<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T create(T t);
    void delete(ID id);
    T update(ID id, T t);
}
