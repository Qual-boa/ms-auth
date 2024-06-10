package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.RelationshipResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.RelationshipSearchDTO;
import com.qualaboa.msauth.dataContract.entities.Relationship;
import com.qualaboa.msauth.dataContract.entities.RelationshipEmbeddedId;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.RelationshipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService {
    
    @Autowired
    private RelationshipRepository repository;


    @Autowired
    private EstablishmentMapper mapper;
    
    @Transactional
    public List<RelationshipResponseDTO> getRelationshipByEstablishmentId(RelationshipSearchDTO request) {
        if(request.getEstablishmentId() == null) throw new IllegalArgumentException("Establishment id is null");
        RelationshipEmbeddedId id = new RelationshipEmbeddedId();
        id.setEstablishmentId(request.getEstablishmentId());
        Relationship example = new Relationship();
        example.setId(id);
        return mapper.relationshipToDTO(repository.findAll(Example.of(example)));
    }
    
    @Transactional
    public List<RelationshipResponseDTO> getRelationshipByEstablishmentIdAndInteractionType(RelationshipSearchDTO request) {
        if(request.getEstablishmentId() == null) throw new IllegalArgumentException("Establishment id is null");
        if(request.getInteractionType() == null) throw new IllegalArgumentException("Interaction type is null");
        RelationshipEmbeddedId id = new RelationshipEmbeddedId();
        id.setEstablishmentId(request.getEstablishmentId());
        id.setInteractionType(request.getInteractionType());
        Relationship example = new Relationship();
        example.setId(id);
        return mapper.relationshipToDTO(repository.findAll(Example.of(example)));
    }
}
