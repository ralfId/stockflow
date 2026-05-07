package com.stockflow.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductSearchDto {

    private String category;

    @NotNull
    @Min(value = 0, message = "El número de página no puede ser negativo")
    private Integer page;

    @NotNull
    @Min(value = 1, message = "El tamaño de página debe ser mayor a 0")
    private Integer size = 10;
}
