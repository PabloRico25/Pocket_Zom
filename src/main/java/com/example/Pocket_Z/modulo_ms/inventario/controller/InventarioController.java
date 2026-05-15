package com.example.Pocket_Z.modulo_ms.inventario.controller;

import com.example.Pocket_Z.modulo_ms.inventario.model.Inventario;
import com.example.Pocket_Z.modulo_ms.inventario.services.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {
    private final InventarioService inventarioService;

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<Inventario> obtenerPorJugador(@PathVariable Long jugadorId) {
        return inventarioService.obtenerPorJugador(jugadorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/jugador/{jugadorId}")
    public Inventario crear(@PathVariable Long jugadorId) {
        return inventarioService.crearInventario(jugadorId);
    }
}