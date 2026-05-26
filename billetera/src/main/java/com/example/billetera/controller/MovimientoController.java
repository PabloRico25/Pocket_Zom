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
            return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.registrarMovimiento(jugadorId, dto));
        } catch (RuntimeException e) {
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