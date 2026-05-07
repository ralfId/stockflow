package com.stockflow.inventory_service.repository;

import com.stockflow.inventory_service.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    Page<Movement> findByProductId(Long productId, Pageable pageable);
}
