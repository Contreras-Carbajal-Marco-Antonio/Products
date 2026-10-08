package com.example1.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example1.products.model.product;;

public interface ProductRepository extends JpaRepository<product, Long> {

}

