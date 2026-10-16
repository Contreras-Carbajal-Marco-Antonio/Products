package com.example1.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example1.products.model.category;

public interface CategoryRepository extends JpaRepository<category, Long>{

}
