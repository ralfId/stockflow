package com.stockflow.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementResponseDto {
    private Long id;
    private Long productId;
    private String type;
    private int quantity;
    private String reason;
    private Instant timestamp;
    private int updatedStock;
}
