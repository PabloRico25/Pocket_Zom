package com.example.perfil.controller;

import com.example.perfil.model.Faccion;
import com.example.perfil.service.FaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facciones")
@RequiredArgsConstructor
public class FaccionController {

    private final FaccionService faccionService;

    @GetMapping
    public ResponseEntity<List<Faccion>> listar() {
        List<Faccion> lista = faccionService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faccion> buscarPorId(@PathVariable Long id) {
        Faccion faccion = faccionService.buscarPorId(id);
        if (faccion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faccion);
    }

    @PostMapping
    public ResponseEntity<Faccion> crear(@Valid @RequestBody Faccion faccion) {
        Faccion nueva = faccionService.crear(faccion);
        if (nueva == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faccion> actualizar(@PathVariable Long id, @Valid @RequestBody Faccion faccion) {
        Faccion actualizada = faccionService.actualizar(id, faccion);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminada = faccionService.eliminar(id);
        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}