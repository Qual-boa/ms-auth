package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InformationRepository extends JpaRepository<Information, UUID> {

    boolean existsByEstablishmentId(UUID establishmentId);
    Information findByEstablishmentId(UUID establishmentId);
}