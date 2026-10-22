package com.example1.products.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example1.products.dto.categoryRequest;
import com.example1.products.dto.categoryResponse;
import com.example1.products.model.category;
import com.example1.products.service.categoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController 
@RequestMapping("/categories")
public class CategoryController {

    private final categoryService service;

    public CategoryController(categoryService service){
        this.service = service;
    }

    private categoryResponse toResponse(category category){
        return new categoryResponse(category.getId(), category.getName());
    }

    private category toEntity(categoryRequest category){
        category categoryEntity = new category();
        categoryEntity.setName(category.name());
        return categoryEntity;
    }
    
    @GetMapping()
    public ResponseEntity<List<categoryResponse>> findAll() {
        List<categoryResponse> list = new ArrayList<>();
        for (category elem : service.findAll()){
            list.add(toResponse(elem));
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<categoryResponse> findById(@PathVariable  Long id) {
        Optional<category> category = service.findById(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(category.get()));
    }
    
    @PostMapping()
    public ResponseEntity<categoryResponse> save(@Valid @RequestBody categoryRequest category){
        category newCategory = service.save(toEntity(category));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<categoryResponse> update(@PathVariable Long id, @Valid @RequestBody categoryRequest categoryUpdate){
        Optional<category> category = service.update(id,
            toEntity(categoryUpdate)
        );
        if (category.isPresent()) {
            return  ResponseEntity.ok(toResponse(category.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}