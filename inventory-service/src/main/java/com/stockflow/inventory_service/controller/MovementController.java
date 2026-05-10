package com.stockflow.inventory_service.controller;

import com.stockflow.inventory_service.dto.ApiResponse;
import com.stockflow.inventory_service.dto.MovementRequestDto;
import com.stockflow.inventory_service.dto.MovementResponseDto;
import com.stockflow.inventory_service.dto.PagedResponseDto;
import com.stockflow.inventory_service.service.MovementService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @PostMapping
    public ResponseEntity<ApiResponse<MovementResponseDto>> registerMovement(
            @Valid @RequestBody MovementRequestDto request) {

        MovementResponseDto response = movementService.registerMovement(request);
        return new ResponseEntity<>( ApiResponse.success(response, HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping("/{productId}/history")
    @RateLimiter(name = "historyRateLimiter")
    public ResponseEntity<ApiResponse<List<MovementResponseDto>>> getHistoryByProductId(
            @PathVariable Long productId) {

        List<MovementResponseDto> history = movementService.getProductMovementHistory(productId);
        return new ResponseEntity<>(ApiResponse.success(history, HttpStatus.OK.value()), HttpStatus.OK);
    }
}
