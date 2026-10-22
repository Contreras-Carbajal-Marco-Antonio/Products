package com.example1.products.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DescuentoRequest {
    @NotNull   
    @Min (1)
    @Max (100)
    Double descuento;

    public DescuentoRequest () {}

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    
}
