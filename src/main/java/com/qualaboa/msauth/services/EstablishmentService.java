package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentSearchDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentUpdateDTO;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.enums.SortOrderEnum;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.services.exceptions.ResourceNotFoundException;
import com.qualaboa.msauth.services.interfaces.IServiceSave;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    
    @Transactional
    public EstablishmentResponseDTO update(EstablishmentUpdateDTO establishmentUpdateDTO) {
        Establishment entity = repository.findById(establishmentUpdateDTO.getId()).orElseThrow(()
                -> new ResourceNotFoundException("Resource not found"));
        entity = repository.save(mapper.fromUpdateToEntity(establishmentUpdateDTO, entity));
        entity.setUpdatedAt(LocalDateTime.now());
        return (EstablishmentResponseDTO) mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<EstablishmentResponseDTO> findAll() {
        List<Establishment> entities = repository.findAll();
        return mapper.toDto(entities);
    }
    
    @Transactional(readOnly = true)
    public EstablishmentResponseDTO findById(UUID id) {
        Optional<Establishment> entity = repository.findById(id);
        return entity.map(establishment -> (EstablishmentResponseDTO) mapper.toDto(establishment)).orElse(null);
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(repository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Resource not found")));
    }

    @Transactional(readOnly = true)
    public List<EstablishmentResponseDTO> findListByFilters(EstablishmentSearchDTO request) {
        var response = mapper.toDto(repository.findAll(createFilter(request)));
        if (request.getSortOrder() != null) {
            return Arrays.stream(sortByPrice(response, request.getSortOrder())).toList();
        }
        return response;
    }

    private Specification<Establishment> createFilter(EstablishmentSearchDTO request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Path<String> names = root.get("fantasyName");

            if (request.getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(names), "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getFoods() != null && !request.getFoods().isEmpty()) {
                predicates.add(root.get("foods").in(request.getFoods()));
            }
            if (request.getDrinks() != null && !request.getDrinks().isEmpty()) {
                predicates.add(root.get("drinks").in(request.getDrinks()));
            }
            if (request.getMusics() != null && !request.getMusics().isEmpty() ) {
                predicates.add(root.get("musics").in(request.getMusics()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private EstablishmentResponseDTO[] sortByPrice(List<EstablishmentResponseDTO> response, SortOrderEnum sortOrder) {
        EstablishmentResponseDTO[] array = new EstablishmentResponseDTO[response.size()];
        quickSort(response.toArray(array), 0, response.size() - 1, Objects.equals(sortOrder, SortOrderEnum.ASCENDING));
        return array;
    }

    private void quickSort(EstablishmentResponseDTO[] response, int low, int high, boolean ascending) {
        if (low < high) {
            int pi = partition(response, low, high, ascending);

            quickSort(response, low, pi - 1, ascending);
            quickSort(response, pi + 1, high, ascending);
        }
    }

    private int partition(EstablishmentResponseDTO[] response, int low, int high, boolean ascending) {
        int pivot = response[high].averageOrderValue();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (ascending ? response[j].averageOrderValue() <= pivot : response[j].averageOrderValue() >= pivot) {
                i++;

                EstablishmentResponseDTO temp = response[i];
                response[i] = response[j];
                response[j] = temp;
            }
        }

        EstablishmentResponseDTO temp = response[i + 1];
        response[i + 1] = response[high];
        response[high] = temp;

        return i + 1;
    }
    
    public FileSystemResource fileCsv(EstablishmentSearchDTO request) throws IOException {
        String fileName = "establishments" + LocalDate.now() + ".csv";
        return recordFileCsv(findListByFilters(request), fileName);
    }

    private FileSystemResource recordFileCsv (List<EstablishmentResponseDTO> establishmentList, String fileName) throws IOException {
        var sb = new StringBuilder();

        String[] headers = {"Nome Fantasia", "Cnpj", "Preço medio"};

        var sj1 = new StringJoiner(";");
        for (String header : headers) {
            sj1.add(header);
        }

        sb.append(sj1 + "\n");

        for(EstablishmentResponseDTO establishment : establishmentList) {
            var sj2 = new StringJoiner(";");
            sj2.add(establishment.fantasyName());
            sj2.add(establishment.cnpj().toString());
            sj2.add(establishment.averageOrderValue().toString());
            sb.append(sj2 + "\n");
        }

        FileWriter file = new FileWriter(fileName);
        file.write(sb.toString());
        file.close();
        return new FileSystemResource(fileName);
    }
    
}