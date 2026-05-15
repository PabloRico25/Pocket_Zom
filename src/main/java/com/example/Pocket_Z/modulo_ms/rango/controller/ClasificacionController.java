package com.example.Pocket_Z.modulo_ms.rango.controller;

import com.example.Pocket_Z.modulo_ms.rango.model.Clasificacion;
import com.example.Pocket_Z.modulo_ms.rango.services.ClasificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class ClasificacionController {
    private final ClasificacionService clasificacionService;

    @PostMapping("/{jugadorId}")
    public Clasificacion crear(@PathVariable Long jugadorId) {
        return clasificacionService.crearClasificacion(jugadorId);
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<Clasificacion> obtenerPorJugador(@PathVariable Long jugadorId) {
        return clasificacionService.obtenerPorJugador(jugadorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{jugadorId}/actualizar")
    public ResponseEntity<Clasificacion> actualizar(@PathVariable Long jugadorId,
                                                    @RequestParam boolean gano,
                                                    @RequestParam int cambioElo) {
        try {
            return ResponseEntity.ok(clasificacionService.actualizarEstadisticas(jugadorId, gano, cambioElo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top")
    public List<Clasificacion> ranking() {
        return clasificacionService.obtenerRanking();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clasificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
