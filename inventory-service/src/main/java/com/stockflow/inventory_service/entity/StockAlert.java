package com.stockflow.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_alert",
        uniqueConstraints = @UniqueConstraint(columnNames = "product_id"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockAlert {

    /*
    * Se especifica que es una entidad de dominio
    * Se agrega la propiedad id para poder persistir las alertas
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "current_stock", nullable = false)
    private int currentStock;

    @Column(name = "min_stock", nullable = false)
    private int minStock;

    @Column(nullable = false, length = 10)
    private String severity; // "LOW" o "CRITICAL"
}
