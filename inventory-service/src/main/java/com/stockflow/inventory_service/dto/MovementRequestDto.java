package com.stockflow.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementRequestDto {

    @NotNull(message = "El ID del producto es obligatorio")
    @Min(value = 1, message = "El ID del producto debe ser mayor a 0")
    private Long productId;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "^(IN|OUT)$", message = "El tipo de movimiento debe ser 'IN' o 'OUT'")
    private String type;

    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private int quantity;

    @NotBlank(message = "La razón del movimiento es obligatoria")
    @Size(min = 3, max = 200, message = "La razón debe tener entre 3 y 200 caracteres")
    private String reason;
}
