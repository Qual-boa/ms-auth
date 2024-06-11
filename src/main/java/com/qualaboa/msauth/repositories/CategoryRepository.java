package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.Category;
import com.qualaboa.msauth.dataContract.entities.CategoryEmbeddedId;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository
        extends CrudRepository<Category, CategoryEmbeddedId> {
    List<Category> findAll(Example<Category> category);
    Optional<Category> findOne(Example<Category> category);
    List<Category> findAll();
}
