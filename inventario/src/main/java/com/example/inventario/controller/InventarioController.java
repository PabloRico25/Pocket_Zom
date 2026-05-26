package com.example.inventario.controller;

import com.example.inventario.dto.InventarioDTO;
import com.example.inventario.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventarios")
@RequiredArgsConstructor
public class InventarioController {
    private final InventarioService inventarioService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<InventarioDTO> crear(@PathVariable Long jugadorId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.crearInventario(jugadorId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<InventarioDTO> obtener(@PathVariable Long jugadorId) {
        InventarioDTO dto = inventarioService.obtenerPorJugador(jugadorId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
}