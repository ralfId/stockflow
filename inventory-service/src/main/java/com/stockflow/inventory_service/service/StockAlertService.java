package com.stockflow.inventory_service.service;

import com.stockflow.inventory_service.dto.PagedResponseDto;
import com.stockflow.inventory_service.dto.ProductResponseDto;
import com.stockflow.inventory_service.dto.StockAlertResponseDto;
import com.stockflow.inventory_service.entity.Product;
import com.stockflow.inventory_service.entity.StockAlert;
import com.stockflow.inventory_service.exception.ProductNotFoundException;
import com.stockflow.inventory_service.mapper.PagedMapper;
import com.stockflow.inventory_service.mapper.ProductMapper;
import com.stockflow.inventory_service.mapper.StockAlertMapper;
import com.stockflow.inventory_service.repository.ProductRepository;
import com.stockflow.inventory_service.repository.StockAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockAlertService {

    private final StockAlertRepository stockAlertRepository;
    private final ProductRepository productRepository;
    private final StockAlertMapper stockAlertMapper;
    private final ProductMapper productMapper;
    private final PagedMapper pagedMapper;

    @Transactional(readOnly = true)
    public PagedResponseDto<ProductResponseDto> getProductAlerts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findLowStockProducts(pageable);

        return pagedMapper.toPagedResponseDto(productPage, productMapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public PagedResponseDto<StockAlertResponseDto> getAlerts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StockAlert> alertsPage = stockAlertRepository.findAll(pageable);

        return pagedMapper.toPagedResponseDto(alertsPage, stockAlertMapper::toResponseDto);
    }

    @Transactional
    public void updateAlert(Product product) {
        if (product.getCurrentStock() <= product.getMinStock()) {
            String severity = (product.getCurrentStock() == product.getMinStock()) ? "LOW" : "CRITICAL";
            createOrUpdateAlert(product, severity);
        } else {
            deleteProductAlerts(product.getId());
        }
    }

    @Transactional
    public void deleteProductAlerts(Long productId){
        stockAlertRepository.findByProductId(productId)
                .ifPresent(stockAlertRepository::delete);
    }

    @Transactional
    public void handleInsufficientStock(Product product) {
        createOrUpdateAlert(product, "CRITICAL");
    }

    private void createOrUpdateAlert(Product product, String severity) {
        StockAlert alert = stockAlertRepository.findByProductId(product.getId())
                .orElse(StockAlert.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .build());

        alert.setCurrentStock(product.getCurrentStock());
        alert.setMinStock(product.getMinStock());
        alert.setSeverity(severity);

        stockAlertRepository.save(alert);
    }
}
