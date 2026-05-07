package com.stockflow.inventory_service.controller;

import com.stockflow.inventory_service.dto.ApiResponse;
import com.stockflow.inventory_service.dto.PagedResponseDto;
import com.stockflow.inventory_service.dto.ProductResponseDto;
import com.stockflow.inventory_service.dto.StockAlertResponseDto;
import com.stockflow.inventory_service.service.StockAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class StockAlertController {

    private final StockAlertService stockAlertService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponseDto<ProductResponseDto>>> getProductAlerts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PagedResponseDto<ProductResponseDto> alerts = stockAlertService.getProductAlerts(page, size);
        return new ResponseEntity<>(ApiResponse.success(alerts, HttpStatus.OK.value()), HttpStatus.OK);
    }
}
