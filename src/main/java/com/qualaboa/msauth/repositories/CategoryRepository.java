package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.Category;
import com.qualaboa.msauth.dataContract.entities.CategoryEmbeddedId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository
        extends CrudRepository<Category, CategoryEmbeddedId> {
    List<Category> findAll(Example<Category> category);
    List<Category> findAll();
}
