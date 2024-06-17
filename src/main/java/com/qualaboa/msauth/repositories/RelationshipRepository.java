package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.Relationship;
import com.qualaboa.msauth.dataContract.entities.RelationshipEmbeddedId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RelationshipRepository  extends CrudRepository<Relationship, RelationshipEmbeddedId> {
    List<Relationship> findAll(Example<Relationship> relationship);
    List<Relationship> findAll();

    @Query("SELECT AVG(r.rate) FROM Relationship r WHERE r.id.establishmentId = :establishmentId")
    Double findAverageRateByEstablishmentId(@Param("establishmentId") UUID establishmentId);

    @Query("SELECT r.id.establishmentId, AVG(r.rate) as avgRate " +
            "FROM Relationship r " +
            "GROUP BY r.id.establishmentId " +
            "ORDER BY avgRate DESC " +
            "LIMIT 3")
    List<Object[]> findTop3EstablishmentsByAverageRate();
}
