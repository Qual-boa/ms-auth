package com.qualaboa.msauth.services.interfaces;

public interface IServiceUpdate <T, RES, ID> {

    RES update(T t, ID id);
}
