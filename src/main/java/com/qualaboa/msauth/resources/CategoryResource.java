package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.establishment.CategoryResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Category;
import com.qualaboa.msauth.dataContract.entities.CategoryEmbeddedId;
import com.qualaboa.msauth.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
    
    @Autowired
    private CategoryService service;
    
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        List<CategoryResponseDTO> categories = service.findAll();
        if (categories.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("byid")
    public ResponseEntity<List<CategoryResponseDTO>> getById(@RequestBody CategoryEmbeddedId id) {
        List<CategoryResponseDTO> category = service.findById(id);
        if (category == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(category);
    }
    
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category categorySaved = service.save(category);
        return ResponseEntity.status(501).body(categorySaved);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody Category category) {
        service.delete(category);
        return ResponseEntity.noContent().build();
    }
}
