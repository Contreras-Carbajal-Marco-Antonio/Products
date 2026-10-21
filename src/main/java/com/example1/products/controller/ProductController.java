package com.example1.products.controller;

import com.example1.products.dto.productRequest;
import com.example1.products.dto.productResponse;
import com.example1.products.model.product;
import com.example1.products.service.productService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final productService service;

    public ProductController(productService service) {
        this.service = service;
    }

    @GetMapping ()
    public ResponseEntity<List<productResponse>> findAll(){
        List<productResponse> list = new ArrayList<>();
        for(product elem : service.findAll()){
            list.add(toResponse(elem));
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<productResponse> findById(@PathVariable Long id) {
        Optional<product> product = service.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(product.get()));
    }

    @PostMapping
    public ResponseEntity<productResponse> save(@Valid @RequestBody productRequest product) {
        product newProduct = service.save(toEntity(product), product.categoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<productResponse> update(@PathVariable Long id,@Valid  @RequestBody productRequest productUpdate) {
        Optional<product> product = service.update(id,
             toEntity(productUpdate),
            productUpdate.categoryId()
        );
        if(product.isPresent()){
            return ResponseEntity.ok(toResponse(product.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private productResponse toResponse(product product){
        return new productResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(),"", 0L);
    }

    private product toEntity(productRequest product){
        product productEntity = new product();
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());
        return productEntity;
    }

}