package com.example.publicacion.controller;

import com.example.publicacion.dto.PublicacionDTO;
import com.example.publicacion.service.PublicacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
    @Autowired
    private PublicacionService publicacionService;

    @GetMapping("/activas")
    public ResponseEntity<List<PublicacionDTO>> listarActivas() {
        return ResponseEntity.ok(publicacionService.listarActivas());
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<PublicacionDTO>> listarPorVendedor(@PathVariable Long vendedorId) {
        return ResponseEntity.ok(publicacionService.listarPorVendedor(vendedorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtener(@PathVariable Long id) {
        PublicacionDTO dto = publicacionService.obtenerPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> crear(@Valid @RequestBody PublicacionDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacionService.crearPublicacion(dto));
        } catch (RuntimeException e) {
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
