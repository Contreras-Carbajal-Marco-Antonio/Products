package com.example1.products.dto;

public record productResponse(
    Long id,
    String name,
    Double price,
    int stock,

    String categoryName,
    Long categoryId
) {

}