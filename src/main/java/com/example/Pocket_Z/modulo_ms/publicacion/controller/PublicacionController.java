package com.example.Pocket_Z.modulo_ms.publicacion.controller;

import com.example.Pocket_Z.modulo_ms.publicacion.model.Publicacion;
import com.example.Pocket_Z.modulo_ms.publicacion.services.PublicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {
    private final PublicacionService publicacionService;

    @GetMapping
    public List<Publicacion> listarActivas() {
        return publicacionService.listarActivas();
    }

    @GetMapping("/vendedor/{vendedorId}")
    public List<Publicacion> listarPorVendedor(@PathVariable Long vendedorId) {
        return publicacionService.listarPorVendedor(vendedorId);
    }

    @PostMapping
    public Publicacion crear(@RequestBody Publicacion publicacion) {
        return publicacionService.guardar(publicacion);
    }

    @PutMapping("/{id}/vender")
    public ResponseEntity<Publicacion> vender(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(publicacionService.marcarComoVendida(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        publicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}