package com.stockflow.inventory_service.service;

import com.stockflow.inventory_service.dto.PagedResponseDto;
import com.stockflow.inventory_service.dto.ProductResponseDto;
import com.stockflow.inventory_service.entity.Product;
import com.stockflow.inventory_service.exception.ProductNotFoundException;
import com.stockflow.inventory_service.mapper.PagedMapper;
import com.stockflow.inventory_service.mapper.ProductMapper;
import com.stockflow.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final PagedMapper pagedMapper;

    @Transactional(readOnly = true)
    public PagedResponseDto<ProductResponseDto> getProducts(String category, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage;

        if (category != null && !category.trim().isEmpty()) {
            productPage = productRepository.findByCategory(category, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        return pagedMapper.toPagedResponseDto(productPage, productMapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));

        return productMapper.toResponseDto(product);
    }
}
