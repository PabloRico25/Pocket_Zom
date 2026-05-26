package com.example.cartacatalogo.controller;

import com.example.cartacatalogo.dto.CartaDTO;
import com.example.cartacatalogo.service.CartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartas")
@RequiredArgsConstructor
public class CartaController {
    private final CartaService cartaService;

    @GetMapping
    public ResponseEntity<List<CartaDTO>> listar() {
        List<CartaDTO> cartas = cartaService.listar();
        if (cartas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cartas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cartaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CartaDTO> obtenerPorCodigo(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(cartaService.obtenerPorCodigo(codigo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CartaDTO> crear(@Valid @RequestBody CartaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaService.crear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CartaDTO dto) {
        try {
            return ResponseEntity.ok(cartaService.actualizar(id, dto));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrada")) return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            cartaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}