package com.stockflow.inventory_service.mapper;

import com.stockflow.inventory_service.dto.StockAlertResponseDto;
import com.stockflow.inventory_service.entity.StockAlert;
import org.springframework.stereotype.Component;

@Component
public class StockAlertMapper {

    public StockAlertResponseDto toResponseDto(StockAlert alert) {
        if (alert == null) {
            return null;
        }
        return StockAlertResponseDto.builder()
                .id(alert.getId())
                .productId(alert.getProductId())
                .productName(alert.getProductName())
                .currentStock(alert.getCurrentStock())
                .minStock(alert.getMinStock())
                .severity(alert.getSeverity())
                .build();
    }
}
