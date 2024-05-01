package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dto.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dto.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.entities.Establishment;
import com.qualaboa.msauth.entities.enums.CategoryTypeEnum;
import com.qualaboa.msauth.entities.enums.DrinkEnum;
import com.qualaboa.msauth.entities.enums.FoodEnum;
import com.qualaboa.msauth.entities.enums.MusicEnum;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.services.interfaces.IServiceSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
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
    public List<EstablishmentResponseDTO> findListByFilters(
            List<CategoryTypeEnum> categories,
            List<MusicEnum> musics,
            List<FoodEnum> foods,
            List<DrinkEnum> drinks){
        if(!categories.isEmpty()){
            Specification filterCategorySpecification = Specification.where()
            for (CategoryTypeEnum category : categories) {

            }
        }
    }
}
