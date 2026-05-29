package com.example.rango.controller;

import com.example.rango.dto.ClasificacionDTO;
import com.example.rango.service.ClasificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ranking")
public class ClasificacionController {
    @Autowired

    private ClasificacionService clasificacionService;

    @GetMapping("/top")
    public ResponseEntity<List<ClasificacionDTO>> top() {
        return ResponseEntity.ok(clasificacionService.obtenerRanking());
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<ClasificacionDTO> obtenerPorJugador(@PathVariable Long jugadorId) {
        ClasificacionDTO dto = clasificacionService.obtenerPorJugador(jugadorId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{jugadorId}")
    public ResponseEntity<ClasificacionDTO> crear(@PathVariable Long jugadorId) {
        ClasificacionDTO nueva = clasificacionService.crearClasificacion(jugadorId);
        if (nueva == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        }
    }

    @PutMapping("/{jugadorId}")
    public ResponseEntity<Void> actualizar(@PathVariable Long jugadorId,@RequestParam boolean esVictoria,@RequestParam(required = false) Integer cambioElo) {
        try {
            int cambio = cambioElo != null ? cambioElo : (esVictoria ? 10 : -5);
            clasificacionService.actualizarRanking(jugadorId, esVictoria, cambio);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
