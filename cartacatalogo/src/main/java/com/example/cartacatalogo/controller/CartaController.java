package com.example.cartacatalogo.controller;

import com.example.cartacatalogo.dto.CartaRequestDTO;
import com.example.cartacatalogo.dto.CartaResponseDTO;
import com.example.cartacatalogo.service.CartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cartas")
@RequiredArgsConstructor
public class CartaController {
    private final CartaService cartaService;

    @GetMapping
    public ResponseEntity<List<CartaResponseDTO>> listar() {
        List<CartaResponseDTO> cartas = cartaService.listar();
        if (cartas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cartas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaResponseDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cartaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            log.warn("Error: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CartaResponseDTO> obtenerPorCodigo(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(cartaService.obtenerPorCodigo(codigo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CartaResponseDTO> crear(@Valid @RequestBody CartaRequestDTO dto) {
        try {
            CartaResponseDTO nueva = cartaService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            log.warn("Error al crear carta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CartaRequestDTO dto) {
        try {
            return ResponseEntity.ok(cartaService.actualizar(id, dto));
        } catch (RuntimeException e) {
            log.warn("Error al actualizar: {}", e.getMessage());
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