package com.example1.products.dto;

import jakarta.validation.constraints.NotBlank;

public record categoryRequest(
    @NotBlank 
    String name
) {
}
