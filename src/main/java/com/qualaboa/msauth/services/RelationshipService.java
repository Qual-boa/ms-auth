package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipSearchDTO;
import com.qualaboa.msauth.dataContract.entities.Relationship;
import com.qualaboa.msauth.dataContract.entities.RelationshipEmbeddedId;
import com.qualaboa.msauth.dataContract.enums.InteractionTypeEnum;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.repositories.RelationshipRepository;
import com.qualaboa.msauth.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RelationshipService {
    
    @Autowired
    private RelationshipRepository repository;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private UserRepository userRepository;

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
    
    @Transactional
    public  RelationshipResponseDTO save(RelationshipCreateDTO request){
        if(request.getInteractionType() == null) throw new IllegalArgumentException("Interaction type is null");
        RelationshipEmbeddedId id = new RelationshipEmbeddedId();
        if(request.getEstablishmentId() != null) id.setEstablishmentId(request.getEstablishmentId());
        if(request.getUserId() != null) id.setUserId(request.getUserId());
        id.setInteractionType(request.getInteractionType());
        Relationship relationship = new Relationship();
        relationship.setId(id);
        if(request.getRate() != null) relationship.setRate(request.getRate());
        if(request.getMessage() != null) relationship.setMessage(request.getMessage());
        return mapper.relationshipToDTO(repository.save(relationship));
    }
    
    public Double getEstablishmentAverageRate(UUID establishmentId) {
        if(establishmentId == null) throw new IllegalArgumentException("Establishment id is null");
        RelationshipEmbeddedId id = new RelationshipEmbeddedId();
        id.setEstablishmentId(establishmentId);
        id.setInteractionType(InteractionTypeEnum.RATE);
        Relationship example = new Relationship();
        example.setId(id);
        return repository.findAverageRateByEstablishmentId(establishmentId);
    }

    public List<Object[]> findTop3EstablishmentsByAverageRate() {
        return repository.findTop3EstablishmentsByAverageRate();
    }
}
