package com.stockflow.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table( name = "movement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false, length = 5)
    private String type; // "IN" o "OUT"

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 200)
    private String reason;

    @Column(nullable = false, updatable = false)
    private Instant timestamp;

    @PrePersist
    public void onPrePersist() {
        this.timestamp = Instant.now();
    }
}
