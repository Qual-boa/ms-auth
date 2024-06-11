package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.CategoryResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Category;
import com.qualaboa.msauth.dataContract.entities.CategoryEmbeddedId;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public CategoryResponseDTO updateCategory(CategoryEmbeddedId id) {
        Category categoryExample = new Category();
        categoryExample.setId(id);

        Optional<Category> optionalCategory = repository.findOne(Example.of(categoryExample));
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            System.out.println("Before update: " + category.toString());

            Integer searchesCount = category.getCountSearches();
            category.setCountSearches(searchesCount == null ? 1 : searchesCount + 1);
            category.setUpdatedAt(LocalDateTime.now());

            Category savedCategory = repository.save(category);

            System.out.println("After update: " + savedCategory.toString());

            return mapper.categoryToDTO(savedCategory);
        }

        return null;
    }
}
