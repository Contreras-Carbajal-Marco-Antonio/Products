package com.example1.products.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example1.products.model.product;
import com.example1.products.service.productService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController 
@RequestMapping("/products")
public class ProductController {

    private final productService service;

    public ProductController(productService service){
        this.service = service;
    }
    
    @GetMapping()
    public ResponseEntity<List<product>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<product> findById(@PathVariable Long id) {
        Optional<product> product = service.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }

    @PostMapping
    public ResponseEntity<product> save(@RequestBody product product) {
        product newProduct = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<product> update(@PathVariable Long id, @RequestBody product product) {
        Optional<product> newProduct = service.update(id,product);
        if (newProduct.isPresent()) {
            return ResponseEntity.ok(newProduct.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<product> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
