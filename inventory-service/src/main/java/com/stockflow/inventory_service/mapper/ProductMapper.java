package com.stockflow.inventory_service.mapper;

import com.stockflow.inventory_service.dto.ProductResponseDto;
import com.stockflow.inventory_service.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDto toResponseDto(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponseDto.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .category(product.getCategory())
                .currentStock(product.getCurrentStock())
                .minStock(product.getMinStock())
                .unitPrice(product.getUnitPrice())
                .build();
    }
}
