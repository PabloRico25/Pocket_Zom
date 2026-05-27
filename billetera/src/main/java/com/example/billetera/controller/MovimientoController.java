package com.example.billetera.controller;

import com.example.billetera.dto.MovimientoDTO;
import com.example.billetera.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
    private final MovimientoService movimientoService;
    @PostMapping("/{jugadorId}")
    public ResponseEntity<MovimientoDTO> registrar(@PathVariable Long jugadorId,
                                                   @Valid @RequestBody MovimientoDTO dto) {
        try {
            MovimientoDTO response = movimientoService.registrarMovimiento(jugadorId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Distinguir errores para devolver 404 o 400
            if (e.getMessage().contains("Cartera no encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<MovimientoDTO>> listar(@PathVariable Long jugadorId) {
        try {
            return ResponseEntity.ok(movimientoService.listarMovimientos(jugadorId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}