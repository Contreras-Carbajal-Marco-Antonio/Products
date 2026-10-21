package com.example1.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example1.products.exception.ResourceNotFoundException;
import com.example1.products.model.category;
import com.example1.products.repository.CategoryRepository;

@Service 
public class categoryService {

    private final CategoryRepository repo;

    public categoryService(CategoryRepository repo){
        this.repo = repo;
    }

    public List<category> findAll(){
        return repo.findAll();
    }

    public Optional<category> findById(Long id){
        return repo.findById(id);
    }

    public category save(category category){
        return repo.save(category);
    }

    public Optional<category> update(Long id, category newCategory){
        category categoryBd = repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id: " + id)
        );
        categoryBd.setName(newCategory.getName());
        return Optional.of(repo.save(categoryBd));
    }

    public boolean delete(Long id){
        if (!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
