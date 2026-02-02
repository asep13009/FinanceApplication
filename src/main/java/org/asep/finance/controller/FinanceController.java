package org.asep.finance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asep.finance.handling.response.ApiResponse;
import org.asep.finance.service.FinanceDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/finance/data")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceDataService service;

    @GetMapping("/{resourceType}")
    public ResponseEntity<ApiResponse<List<?>>> get(
            @PathVariable String resourceType) {
        log.info("resourceType from client >>> {}",resourceType);
        return ResponseEntity.ok(
                ApiResponse.success(service.getData(resourceType))
        );
    }
}
