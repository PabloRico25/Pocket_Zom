package com.example.cartacatalogo.controller;

import com.example.cartacatalogo.model.Carta;
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
    public ResponseEntity<List<Carta>> listar() {
        List<Carta> cartas = cartaService.listar();
        if (cartas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cartas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Carta> obtenerPorId(@PathVariable Long id) {
        Carta carta = cartaService.obtenerPorId(id);
        return carta != null ? ResponseEntity.ok(carta) : ResponseEntity.notFound().build();
    }
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Carta> obtenerPorCodigo(@PathVariable String codigo) {
        Carta carta = cartaService.obtenerPorCodigo(codigo);
        return carta != null ? ResponseEntity.ok(carta) : ResponseEntity.notFound().build();
    }
    @GetMapping("/codigo/{codigo}/existe")
    public ResponseEntity<Boolean> existePorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(cartaService.existePorCodigo(codigo));
    }
    @PostMapping
    public ResponseEntity<Carta> crear(@Valid @RequestBody Carta carta) {
        Carta creada = cartaService.crear(carta);
        if (creada == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Carta> actualizar(@PathVariable Long id, @Valid @RequestBody Carta carta) {
        Carta actualizada = cartaService.actualizar(id, carta);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = cartaService.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}