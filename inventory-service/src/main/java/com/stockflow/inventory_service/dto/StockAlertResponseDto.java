package com.stockflow.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAlertResponseDto {
    private Long id;
    private Long productId;
    private String productName;
    private int currentStock;
    private int minStock;
    private String severity;
}
