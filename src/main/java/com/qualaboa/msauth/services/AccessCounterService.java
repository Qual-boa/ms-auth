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
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public FileSystemResource fileCsv(UUID establishmentId) throws IOException {
        DashboardDataDTO dashboardDataDTO = getDashboardDataByEstablishmentId(establishmentId);

        String fileName = "dashboardData" + LocalDate.now() + ".csv";
        return recordFileCsv(dashboardDataDTO, fileName);
    }

    public String fileCsvFromString(UUID establishmentId) throws IOException {
        DashboardDataDTO dashboardDataDTO = getDashboardDataByEstablishmentId(establishmentId);
        if(dashboardDataDTO == null) {
            throw new ResourceNotFoundException("Dashboard not found");
        }
        return dashboardDataDTO.toCSV();
    }

    private FileSystemResource recordFileCsv(DashboardDataDTO dashboardDataDTO, String fileName) throws IOException {
        String csvContent = dashboardDataDTO.toCSV();
        FileWriter file = new FileWriter(fileName);
        file.write(csvContent);
        file.close();
        return new FileSystemResource(fileName);
    }

    public DashboardDataDTO getDashboardDataByEstablishmentId(UUID establishmentId) {
        DashboardDataDTO dashboardDataDTO = new DashboardDataDTO();
        Double averageClicks = repository.findAverageClicksPerMonth(establishmentId);
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

        Stack<Map<String, Object>> stack = new Stack<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            Map<String, Object> dayResult = new HashMap<>();
            dayResult.put("date", date);
            dayResult.put("count", clicksPerDayMap.getOrDefault(date, 0L));
            stack.push(dayResult);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
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
