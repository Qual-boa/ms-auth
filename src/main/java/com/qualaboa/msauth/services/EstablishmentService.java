package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentSearchDto;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.services.interfaces.IServiceSave;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstablishmentService implements IServiceSave<EstablishmentCreateDTO, EstablishmentResponseDTO> {

    @Autowired
    private EstablishmentRepository repository;

    @Autowired
    private EstablishmentMapper mapper;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public EstablishmentResponseDTO save(EstablishmentCreateDTO establishmentCreateDTO) {
        Establishment entity = mapper.toEntity(establishmentCreateDTO);
        entity.setCreatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return (EstablishmentResponseDTO) mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<EstablishmentResponseDTO> findListByFilters(EstablishmentSearchDto request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Establishment> criteriaQuery = criteriaBuilder.createQuery(Establishment.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Establishment> establishment = criteriaQuery.from(Establishment.class);
        
        if (request.getName() != null) {
            predicates.add(criteriaBuilder.equal(establishment.get("name"), "%" + request.getName() + "+"));
        }
        if (!request.getFoods().isEmpty()) {
            predicates.add(criteriaBuilder.in(establishment.get("foods")));
        }
        if (!request.getDrinks().isEmpty()) {
            predicates.add(criteriaBuilder.in(establishment.get("drinks")));
        }
        if (!request.getMusics().isEmpty()) {
            predicates.add(criteriaBuilder.in(establishment.get("musics")));
        }
        
        TypedQuery<Establishment> query = entityManager.createQuery(criteriaQuery);
        List<EstablishmentResponseDTO> result = mapper.toDto(query.getResultList());
        return result;
    }
}