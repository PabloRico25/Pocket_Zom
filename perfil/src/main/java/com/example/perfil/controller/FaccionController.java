package com.example.perfil.controller;

import com.example.perfil.dto.FaccionDTO;
import com.example.perfil.service.FaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facciones")
@RequiredArgsConstructor
public class FaccionController {
    private final FaccionService faccionService;

    @GetMapping
    public ResponseEntity<List<FaccionDTO>> listar() {
        return ResponseEntity.ok(faccionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaccionDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(faccionService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FaccionDTO> crear(@Valid @RequestBody FaccionDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(faccionService.crear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaccionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody FaccionDTO dto) {
        try {
            return ResponseEntity.ok(faccionService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            faccionService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}