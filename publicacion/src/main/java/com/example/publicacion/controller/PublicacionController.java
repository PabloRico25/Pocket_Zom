package com.example.publicacion.controller;

import com.example.publicacion.dto.PublicacionRequestDTO;
import com.example.publicacion.dto.PublicacionResponseDTO;
import com.example.publicacion.service.PublicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {
    private final PublicacionService publicacionService;

    @GetMapping("/activas")
    public ResponseEntity<List<PublicacionResponseDTO>> listarActivas() {
        List<PublicacionResponseDTO> list = publicacionService.listarActivas();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<PublicacionResponseDTO>> listarPorVendedor(@PathVariable Long vendedorId) {
        List<PublicacionResponseDTO> list = publicacionService.listarPorVendedor(vendedorId);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionResponseDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(publicacionService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{vendedorId}")
    public ResponseEntity<PublicacionResponseDTO> crear(@PathVariable Long vendedorId,
                                                        @Valid @RequestBody PublicacionRequestDTO dto) {
        try {
            PublicacionResponseDTO nueva = publicacionService.crearPublicacion(vendedorId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            publicacionService.eliminarPublicacion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}