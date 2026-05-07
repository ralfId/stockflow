package com.stockflow.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table( name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String sku;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 60)
    private String category;

    @Column(nullable = false, name = "current_stock")
    private int currentStock;

    @Column(nullable = false, name = "min_stock")
    private int minStock;

    @Column(nullable = false, name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
}
