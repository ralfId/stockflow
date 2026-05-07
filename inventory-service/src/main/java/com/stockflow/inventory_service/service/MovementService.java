package com.stockflow.inventory_service.service;

import com.stockflow.inventory_service.dto.MovementRequestDto;
import com.stockflow.inventory_service.dto.MovementResponseDto;
import com.stockflow.inventory_service.dto.PagedResponseDto;
import com.stockflow.inventory_service.entity.Movement;
import com.stockflow.inventory_service.entity.Product;
import com.stockflow.inventory_service.exception.InsufficientStockException;
import com.stockflow.inventory_service.exception.ProductNotFoundException;
import com.stockflow.inventory_service.mapper.MovementMapper;
import com.stockflow.inventory_service.mapper.PagedMapper;
import com.stockflow.inventory_service.repository.MovementRepository;
import com.stockflow.inventory_service.repository.ProductRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final StockAlertService stockAlertService;
    private final MovementMapper movementMapper;
    private final PagedMapper pagedMapper;

    @Transactional
    @Retry(name = "movementRetry")
    public MovementResponseDto registerMovement(MovementRequestDto request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Producto con ID " + request.getProductId() + " no encontrado"));

        int newStock;

        if ("IN".equals(request.getType())) {
            newStock = product.getCurrentStock() + request.getQuantity();
        } else {
            if (product.getCurrentStock() < request.getQuantity()) {
                stockAlertService.handleInsufficientStock(product);
                throw new InsufficientStockException(
                        "Stock insuficiente para el producto '" + product.getName()
                                + "'. Stock actual: " + product.getCurrentStock()
                                + ", cantidad solicitada: " + request.getQuantity());
            }
            newStock = product.getCurrentStock() - request.getQuantity();
        }

        product.setCurrentStock(newStock);
        productRepository.save(product);
        
        // Actualizar alertas de stock
        stockAlertService.updateAlert(product);

        Movement movement = Movement.builder()
                .productId(product.getId())
                .type(request.getType())
                .quantity(request.getQuantity())
                .reason(request.getReason())
                .build();

        Movement savedMovement = movementRepository.save(movement);

        MovementResponseDto responseDto = movementMapper.toResponseDto(savedMovement);
        responseDto.setUpdatedStock(newStock);
        return responseDto;
    }

    @Transactional(readOnly = true)
    public PagedResponseDto<MovementResponseDto> getProductMovementHistory(Long productId, int page, int size) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Producto con ID " + productId + " no encontrado");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<Movement> movementPage = movementRepository.findByProductId(productId, pageable);

        if (movementPage.isEmpty()) {
            throw new ProductNotFoundException("No se encontraron movimientos para el producto");
        }

        return pagedMapper.toPagedResponseDto(movementPage, movementMapper::toResponseDto);
    }
}
