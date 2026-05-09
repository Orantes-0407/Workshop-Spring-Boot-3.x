package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.domain.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/simulaciones/prestamo-simulacion")
@Tag(name = "Simulaciones", description = "Endpoints para simulaciones de préstamos")
@Validated
public class PrestamoSimulacionController {

    private final PrestamoService prestamoService;

    @Autowired
    public PrestamoSimulacionController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    @Operation(summary = "Simular préstamo", description = "Calcula la cuota mensual, el interés total y el monto total a pagar para un préstamo.")
    public ResponseEntity<PrestamoResponse> simularPrestamo(@Valid @RequestBody PrestamoRequest request) {
        PrestamoResponse response = prestamoService.simular(request);
        return ResponseEntity.ok(response);
    }
}