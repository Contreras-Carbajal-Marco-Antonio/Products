package com.example1.products.dto;

public record productResponse(
    Long id,
    String name,
    Long price,
    int stock,

    String categoryName,
    Long categoryId
) {

}