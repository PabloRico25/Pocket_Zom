package com.example.inventario.controller;

import com.example.inventario.dto.InventarioResponseDTO;
import com.example.inventario.service.InventarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {
    private final InventarioService inventarioService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<InventarioResponseDTO> crear(@PathVariable Long jugadorId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(inventarioService.toDTO(inventarioService.crearInventario(jugadorId)));
        } catch (RuntimeException e) {
            log.warn("Error al crear inventario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<InventarioResponseDTO> obtener(@PathVariable Long jugadorId) {
        return inventarioService.obtenerPorJugador(jugadorId)
                .map(i -> ResponseEntity.ok(inventarioService.toDTO(i)))
                .orElse(ResponseEntity.notFound().build());
    }
}