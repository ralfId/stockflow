package com.stockflow.inventory_service.controller;

import com.stockflow.inventory_service.dto.ApiResponse;
import com.stockflow.inventory_service.dto.PagedResponseDto;
import com.stockflow.inventory_service.dto.ProductResponseDto;
import com.stockflow.inventory_service.dto.ProductSearchDto;
import com.stockflow.inventory_service.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponseDto<ProductResponseDto>>> getProducts(
            @Valid @RequestBody ProductSearchDto searchDto) {

        PagedResponseDto<ProductResponseDto> products = productService.getProducts(
                searchDto.getCategory(),
                searchDto.getPage(),
                searchDto.getSize()
        );
        return new ResponseEntity<>(ApiResponse.success(products, HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(
            @PathVariable @Min(value = 1, message = "El ID del producto debe ser mayor a 0") Long id) {

        ProductResponseDto product = productService.getProductById(id);
        return new ResponseEntity<>(ApiResponse.success(product, HttpStatus.OK.value()), HttpStatus.OK);
    }
}
