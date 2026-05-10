package com.stockflow.inventory_service.mapper;

import com.stockflow.inventory_service.dto.MovementResponseDto;
import com.stockflow.inventory_service.entity.Movement;
import org.springframework.stereotype.Component;

@Component
public class MovementMapper {

    public MovementResponseDto toResponseDto(Movement movement) {
        if (movement == null) {
            return null;
        }
        return MovementResponseDto.builder()
                .id(movement.getId())
                .productId(movement.getProductId())
                .type(movement.getType())
                .quantity(movement.getQuantity())
                .reason(movement.getReason())
                .timestamp(movement.getTimestamp())
                .updatedStock(movement.getQuantity())
                .build();
    }
}
