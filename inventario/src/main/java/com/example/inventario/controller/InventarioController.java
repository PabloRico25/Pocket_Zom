package com.example.inventario.controller;

import com.example.inventario.model.Inventario;
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
    @GetMapping("/{idJugador}")
    public ResponseEntity<Inventario> buscar(@PathVariable Long idJugador) {
        Inventario inv = inventarioService.buscarPorJugador(idJugador);
        if (inv == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inv);
    }
    @PostMapping("/{idJugador}")
    public ResponseEntity<Inventario> crear(@PathVariable Long idJugador) {
        Inventario nuevo = inventarioService.crear(idJugador);
        if (nuevo == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}