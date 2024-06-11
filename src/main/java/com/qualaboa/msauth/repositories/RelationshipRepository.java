package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.Relationship;
import com.qualaboa.msauth.dataContract.entities.RelationshipEmbeddedId;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RelationshipRepository  extends CrudRepository<Relationship, RelationshipEmbeddedId> {
    List<Relationship> findAll(Example<Relationship> relationship);
    List<Relationship> findAll();
}
