package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstablishmentRepository
        extends JpaRepository<Establishment, UUID>,
                JpaSpecificationExecutor<Establishment> {
}
