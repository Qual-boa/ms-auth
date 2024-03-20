package com.qualaboa.msauth.services;

import com.qualaboa.msauth.entities.Establishment;

import java.util.List;
import java.util.UUID;

public class EstablishmentService implements IService<Establishment, UUID> {
    @Override
    public List<Establishment> findAll() {
        return null;
    }

    @Override
    public Establishment findById(UUID uuid) {
        return null;
    }

    @Override
    public Establishment create(Establishment establishment) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Establishment update(UUID uuid, Establishment establishment) {
        return null;
    }
}
