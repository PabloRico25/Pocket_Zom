package com.example.billetera.controller;

import com.example.billetera.dto.MovimientoRequestDTO;
import com.example.billetera.dto.MovimientoResponseDTO;
import com.example.billetera.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
    private final MovimientoService movimientoService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<MovimientoResponseDTO> registrar(
            @PathVariable Long jugadorId,
            @Valid @RequestBody MovimientoRequestDTO dto) {
        try {
            MovimientoResponseDTO response = movimientoService.registrarMovimiento(jugadorId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            log.error("Error al registrar movimiento: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<MovimientoResponseDTO>> listar(@PathVariable Long jugadorId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movimientoService.listarMovimientos(jugadorId));
        } catch (RuntimeException e) {
            log.error("Error al listar movimientos: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}