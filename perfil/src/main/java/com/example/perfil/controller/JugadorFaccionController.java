package com.example.perfil.controller;

import com.example.perfil.service.JugadorFaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugador-faccion")
@RequiredArgsConstructor
public class JugadorFaccionController {
    private final JugadorFaccionService jugadorFaccionService;

    @PostMapping("/{jugadorId}/{faccionId}")
    public ResponseEntity<Void> unir(@PathVariable Long jugadorId, @PathVariable Long faccionId) {
        try {
            jugadorFaccionService.unir(jugadorId, faccionId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{jugadorId}/{faccionId}")
    public ResponseEntity<Void> salir(@PathVariable Long jugadorId, @PathVariable Long faccionId) {
        try {
            jugadorFaccionService.salir(jugadorId, faccionId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<Long>> listarFacciones(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(jugadorFaccionService.listarFaccionesDeJugador(jugadorId));
    }
}