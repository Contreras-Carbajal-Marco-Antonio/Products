package com.example1.products.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example1.products.repository.ProductRepository;
import com.example1.products.model.product;

@Service 
public class productService {
    
    private final ProductRepository repo;

    public productService(ProductRepository repo){
        this.repo = repo;
    }

    public List<product> findAll(){
        return repo.findAll();
    }

    public Optional<product> findById(Long id){
        return repo.findById(id);
    }

    public product save(product product){
        return repo.save(product);
    }

    public Optional<product> update(Long id, product newProduct){
        Optional<product> optional = repo.findById(id);
        product productBd = optional.get();
        productBd.setName(newProduct.getName());
        productBd.setPrice(newProduct.getPrice());
        productBd.setStock(newProduct.getStock());
        save(productBd);
        return Optional.of(save(productBd));
    }

    public boolean delete(Long id){
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
