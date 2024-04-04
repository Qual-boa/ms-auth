package com.qualaboa.msauth.services.interfaces;

public interface IServiceFindById<T, ID> {
    T findById(ID id);
}
