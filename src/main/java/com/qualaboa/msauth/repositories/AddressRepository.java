package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.Address;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByEstablishment(Establishment establishment);
    List<Address> findByUser(User user);

    void deleteAllByEstablishment(Establishment establishment);
    void deleteAllByUser(User user);

    boolean existsByEstablishmentId(UUID establishmentId);

    boolean existsByUserId(UUID userId);
}
