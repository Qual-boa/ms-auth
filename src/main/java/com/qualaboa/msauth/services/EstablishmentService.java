package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.*;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipResponseDTO;
import com.qualaboa.msauth.dataContract.entities.*;
import com.qualaboa.msauth.dataContract.enums.InteractionTypeEnum;
import com.qualaboa.msauth.dataContract.enums.SortOrderEnum;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.AccessCounterRepository;
import com.qualaboa.msauth.repositories.CategoryRepository;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.repositories.RelationshipRepository;
import com.qualaboa.msauth.services.exceptions.ResourceNotFoundException;
import com.qualaboa.msauth.services.interfaces.IServiceSave;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Example;
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
    private CategoryRepository categoryRepository;
    @Autowired
    private RelationshipService relationshipService;
    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    private AccessCounterRepository accessCounterRepository;
    @Autowired
    private CategoryService categoryService;

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
    public EstablishmentResponseDTO saveCategories(EstablishmentCategoryDTO request) throws BadRequestException {
        Establishment entity = repository.findById(request.getEstablishmentId()).orElseThrow(()
                -> new ResourceNotFoundException("Establishment not found"));
        if(request.getCategories() == null || request.getCategories().isEmpty()) throw new BadRequestException();
        
        List<Category> categories = new ArrayList<>();
        for(CategoryEmbeddedId id : request.getCategories()) {
            Category categoryExample = new Category();
            categoryExample.setId(id);
            List<Category> categoryFound = categoryRepository.findAll(Example.of(categoryExample));
            categories.addAll(categoryFound);
        }
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCategories(categories);
        repository.save(entity);
        return (EstablishmentResponseDTO) mapper.toDto(entity);
    }
    
    @Transactional
    public EstablishmentResponseDTO saveRelationship(RelationshipCreateDTO request){
        Establishment entity = repository.findById(request.getEstablishmentId()).orElseThrow(()
                -> new ResourceNotFoundException("Establishment not found"));
        entity.getRelationships().add(mapper.relationshipToEntity(relationshipService.save(request)));
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
    
    @Transactional
    public EstablishmentResponseDTO findById(UUID id) {
        Establishment entity = repository.findById(id).orElseThrow(() 
                -> new ResourceNotFoundException("Establishment not found"));
        AccessCounter newAccess = new AccessCounter();
        newAccess.setAccessedAt(LocalDateTime.now());
        newAccess.setEstablishmentId(id);
        entity.getAccess().add(newAccess);
        accessCounterRepository.save(newAccess);
        return (EstablishmentResponseDTO) mapper.toDto(entity);
    }
    
    @Transactional
    public EstablishmentResponseDTO getEstablishmentByUserId(UUID userId){
        if(userId == null) throw new IllegalArgumentException("user id is null");
        RelationshipEmbeddedId id = new RelationshipEmbeddedId();
        id.setUserId(userId);
        id.setInteractionType(InteractionTypeEnum.EMPLOYEE);
        Relationship example = new Relationship();
        example.setId(id);
        List<RelationshipResponseDTO> relationshipsFound = mapper.relationshipToDTO(relationshipRepository.findAll(Example.of(example)));
        Optional<Establishment> establishmentFound = repository.findById(relationshipsFound.get(0).getEstablishmentId());
        if(establishmentFound.isEmpty()) throw new ResourceNotFoundException("Establishment not found");
        return (EstablishmentResponseDTO) mapper.toDto(establishmentFound.get());
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(repository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Resource not found")));
    }
    
    @Transactional
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

            if (request.getCategories() != null && !request.getCategories().isEmpty()) {
                List<Predicate> categoryPredicates = new ArrayList<>();
                Join<Establishment, Category> join = root.join("categories");

                for (CategoryEmbeddedId id : request.getCategories()) {
                    categoryService.updateCategory(id);
                    Predicate predicateCategoryType = criteriaBuilder.equal(join.get("id").get("categoryType"), id.getCategoryType());
                    Predicate predicateCategory = criteriaBuilder.equal(join.get("id").get("category"), id.getCategory());

                    Predicate categoryPredicate = criteriaBuilder.and(predicateCategoryType, predicateCategory);
                    categoryPredicates.add(categoryPredicate);
                }
                predicates.add(criteriaBuilder.or(categoryPredicates.toArray(new Predicate[0])));
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
        int pivot = response[high].getAverageOrderValue();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (ascending ? response[j].getAverageOrderValue() <= pivot : response[j].getAverageOrderValue() >= pivot) {
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
            sj2.add(establishment.getFantasyName());
            sj2.add(establishment.getCnpj().toString());
            sj2.add(establishment.getAverageOrderValue().toString());
            sb.append(sj2 + "\n");
        }

        FileWriter file = new FileWriter(fileName);
        file.write(sb.toString());
        file.close();
        return new FileSystemResource(fileName);
    }
}