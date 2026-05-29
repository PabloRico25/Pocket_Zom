package com.example.publicacion.controller;

import com.example.publicacion.dto.PublicacionDTO;
import com.example.publicacion.service.PublicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {
    private final PublicacionService publicacionService;

    @GetMapping("/activas")
    public ResponseEntity<List<PublicacionDTO>> listarActivas() {
        List<PublicacionDTO> lista = publicacionService.listarActivas();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<PublicacionDTO>> listarPorVendedor(@PathVariable Long idVendedor) {
        return ResponseEntity.ok(publicacionService.listarPorVendedor(idVendedor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> buscar(@PathVariable Long id) {
        PublicacionDTO dto = publicacionService.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> crear(@Valid @RequestBody PublicacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionService.crear(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        publicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
