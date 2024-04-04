package com.qualaboa.msauth.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServiceFindAll<T> {
    Page<T> findAll(Pageable pageable);
}
