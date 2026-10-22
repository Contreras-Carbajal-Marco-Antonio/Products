package com.example1.products.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example1.products.repository.CategoryRepository;
import com.example1.products.repository.ProductRepository;
import com.example1.products.exception.ResourceNotFoundException;
import com.example1.products.exception.StockException;
import com.example1.products.model.category;
import com.example1.products.model.product;

@Service 
public class productService {
    
    private final ProductRepository repo;

    private final CategoryRepository repoCategory;

    public productService(ProductRepository repoProduct, CategoryRepository repoCategory){
        this.repo = repoProduct;
        this.repoCategory = repoCategory;
    }

    public List<product> findAll(){
        return repo.findAll();
    }

    public Optional<product> findById(Long id){
        return repo.findById(id);
    }

    public product save(product product, Long categoryId){
        category category = repoCategory.
            findById(categoryId).
            orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id: " + categoryId)
        );
        product.setCategory(category);
        return repo.save(product);
    }

    public product update(Long id, product newProduct, Long categoryId){
        product productBd = repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product not found with id: " + id)
        );
        category category = repoCategory.findById(categoryId).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id: " + categoryId)
        );
        //product productBd = optional.get();
        productBd.setName(newProduct.getName());
        productBd.setPrice(newProduct.getPrice());
        productBd.setStock(newProduct.getStock());
        productBd.setCategory(category);
        return repo.save(productBd);
    }

    public boolean delete(Long id){
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }

    public product descuento(Long id, Double desc){
        product product = findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product not found with id: " + id)
        );
        if (product.getStock() < 1) {
            throw new StockException("No se puede aplicar un descuento a un producto con stock 0.");
        }
        Double descuento = product.getPrice() * (desc / 100.0);
        Double newPrice = product.getPrice() - descuento;
        product.setPrice(newPrice);
        return repo.save(product);
    }
    
}
