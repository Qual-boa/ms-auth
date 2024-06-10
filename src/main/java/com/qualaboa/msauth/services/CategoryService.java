package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.CategoryResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Category;
import com.qualaboa.msauth.dataContract.entities.CategoryEmbeddedId;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private EstablishmentMapper mapper;
    
    public List<CategoryResponseDTO> findAll() {
        return mapper.categoryToDTO(repository.findAll());
    }
    
    public List<CategoryResponseDTO> findById(CategoryEmbeddedId id) {
        Category categoryExample = new Category();
        categoryExample.setId(id);
        return mapper.categoryToDTO(repository.findAll(Example.of(categoryExample)));
    }
    
    public Category save(Category category) {
        return repository.save(category);
    }
    
    public void delete(Category category) {
        repository.delete(category);
    }
}
