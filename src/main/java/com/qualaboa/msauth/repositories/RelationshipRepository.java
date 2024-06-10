package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.Relationship;
import com.qualaboa.msauth.dataContract.entities.RelationshipEmbeddedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipEmbeddedId> {
}
