package com.example.perfil.controller;

import com.example.perfil.dto.FaccionRequestDTO;
import com.example.perfil.dto.FaccionResponseDTO;
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
    public ResponseEntity<List<FaccionResponseDTO>> listar() {
        List<FaccionResponseDTO> list = faccionService.listar();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaccionResponseDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(faccionService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FaccionResponseDTO> crear(@Valid @RequestBody FaccionRequestDTO dto) {
        try {
            FaccionResponseDTO nueva = faccionService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaccionResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody FaccionRequestDTO dto) {
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