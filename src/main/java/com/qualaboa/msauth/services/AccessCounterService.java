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
import java.time.ZoneId;
import java.util.*;
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
        
        dashboardDataDTO.setClicksPerDayLast7Days(getClicksPerDayLast7Days());
        dashboardDataDTO.setRate(relationshipRepository.findAverageRateByEstablishmentId(establishmentId));
        dashboardDataDTO.setAverageClicksPerMonth(averageClicks == null ? 0 : averageClicks);
        dashboardDataDTO.setDayOfWeekWithMostClicks(repository.findDayOfWeekWithMostClicks());
        dashboardDataDTO.setFindHourWithMostClicks(repository.findHourWithMostClicks());
        dashboardDataDTO.setFavoriteCount(favoriteCount);
        dashboardDataDTO.setCategoriesSearches(mapper.categoryToDTO(categoryRepository.findAll()));
        return dashboardDataDTO;
    }

    public List<Map<String, Object>> getClicksPerDayLast7Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        List<Map<String, Object>> clicksPerDay = repository.findClicksPerDayLast7Days(startDate);
        Map<LocalDate, Long> clicksPerDayMap = clicksPerDay.stream()
                .collect(Collectors.toMap(
                        entry -> (LocalDate) entry.get("date"),
                        entry -> (Long) entry.get("count")
                ));

        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            Map<String, Object> dayResult = new HashMap<>();
            dayResult.put("date", date);
            dayResult.put("count", clicksPerDayMap.getOrDefault(date, 0L));
            result.add(dayResult);
        }

        return result;
    }
    
    private Integer getFavoriteCount(UUID establishmentId) {
        RelationshipEmbeddedId id = new RelationshipEmbeddedId();
        id.setEstablishmentId(establishmentId);
        id.setInteractionType(InteractionTypeEnum.FAVORITE);
        Relationship example = new Relationship();
        example.setId(id);
        return relationshipRepository.findAll(Example.of(example)).size();
    }
}
