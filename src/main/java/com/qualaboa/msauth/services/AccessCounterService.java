package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.Access.DashboardDataDTO;
import com.qualaboa.msauth.dataContract.entities.AccessCounter;
import com.qualaboa.msauth.dataContract.entities.Relationship;
import com.qualaboa.msauth.dataContract.entities.RelationshipEmbeddedId;
import com.qualaboa.msauth.dataContract.enums.InteractionTypeEnum;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.AccessCounterRepository;
import com.qualaboa.msauth.repositories.CategoryRepository;
import com.qualaboa.msauth.repositories.RelationshipRepository;
import com.qualaboa.msauth.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AccessCounterService {
    @Autowired
    private AccessCounterRepository repository;
    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private EstablishmentMapper mapper;
    
    public List<AccessCounter> getAccessCounterByEstablishmentId(UUID establishmentId) {
        List<AccessCounter> establishmentAccess = repository.findAllByEstablishmentId(establishmentId);
        if (establishmentAccess.isEmpty()) throw new ResourceNotFoundException("Establishment not found or without access counter");
        return establishmentAccess;
    }
    
    public DashboardDataDTO getDashboardDataByEstablishmentId(UUID establishmentId) {
        DashboardDataDTO dashboardDataDTO = new DashboardDataDTO();
        Double averageClicks = repository.findAverageClicksPerMonth();
        Integer favoriteCount = getFavoriteCount(establishmentId);
        
        dashboardDataDTO.setAverageClicksPerMonth(averageClicks == null ? 0 : averageClicks);
        dashboardDataDTO.setDayOfWeekWithMostClicks(repository.findDayOfWeekWithMostClicks());
        dashboardDataDTO.setClicksPerDayLast30Days(getClicksPerDayLast30Days(LocalDateTime.now()));
        dashboardDataDTO.setFindHourWithMostClicks(repository.findHourWithMostClicks());
        dashboardDataDTO.setFavoriteCount(favoriteCount);
        dashboardDataDTO.setCategoriesSearches(mapper.categoryToDTO(categoryRepository.findAll()));
        return dashboardDataDTO;
    }
    
    private Integer getFavoriteCount(UUID establishmentId) {
        RelationshipEmbeddedId id = new RelationshipEmbeddedId();
        id.setEstablishmentId(establishmentId);
        id.setInteractionType(InteractionTypeEnum.FAVORITE);
        Relationship example = new Relationship();
        example.setId(id);
        return relationshipRepository.findAll(Example.of(example)).size();
    }
    
    public List<Object[]> getClicksPerDayLast30Days(LocalDateTime startDate) {
        // Calcula a data de 30 dias atrás a partir da data fornecida
        LocalDateTime endDate = startDate.minusDays(30);

        // Chama o método do repositório com as datas calculadas
        return repository.findClicksPerDay(startDate, endDate);
    }
}
