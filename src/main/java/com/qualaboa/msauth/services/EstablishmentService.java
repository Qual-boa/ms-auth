package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentSearchDto;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.services.interfaces.IServiceSave;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    @Transactional
    public EstablishmentResponseDTO save(EstablishmentCreateDTO establishmentCreateDTO) {
        Establishment entity = mapper.toEntity(establishmentCreateDTO);
        entity.setCreatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return (EstablishmentResponseDTO) mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<EstablishmentResponseDTO> findAll() {
        List<Establishment> entities = repository.findAll();
        return mapper.toDto(entities);
    }

    @Transactional(readOnly = true)
    public List<EstablishmentResponseDTO> findListByFilters(EstablishmentSearchDto request) {
        return mapper.toDto(repository.findAll(CreateFilter(request)));
    }

    private Specification<Establishment> CreateFilter(EstablishmentSearchDto request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("fantasyName"), "%" + request.getName() + "%"));
            }
            if (!request.getFoods().isEmpty()) {
                predicates.add(root.get("foods").in(request.getFoods()));
            }
            if (!request.getDrinks().isEmpty()) {
                predicates.add(root.get("drinks").in(request.getDrinks()));
            }
            if (!request.getMusics().isEmpty() ) {
                predicates.add(root.get("musics").in(request.getMusics()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}