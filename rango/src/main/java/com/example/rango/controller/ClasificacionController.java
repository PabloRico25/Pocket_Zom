package com.example.rango.controller;

import com.example.rango.dto.ClasificacionRequestDTO;
import com.example.rango.dto.ClasificacionResponseDTO;
import com.example.rango.service.ClasificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class ClasificacionController {
    private final ClasificacionService clasificacionService;

    @GetMapping("/top")
    public ResponseEntity<List<ClasificacionResponseDTO>> ranking() {
        List<ClasificacionResponseDTO> ranking = clasificacionService.obtenerRanking();
        if (ranking.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<ClasificacionResponseDTO> obtenerPorJugador(@PathVariable Long jugadorId) {
        try {
            return ResponseEntity.ok(clasificacionService.obtenerPorJugador(jugadorId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{jugadorId}")
    public ResponseEntity<ClasificacionResponseDTO> crear(@PathVariable Long jugadorId) {
        try {
            ClasificacionResponseDTO nueva = clasificacionService.crearClasificacion(jugadorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{jugadorId}")
    public ResponseEntity<ClasificacionResponseDTO> actualizar(@PathVariable Long jugadorId,
                                                               @Valid @RequestBody ClasificacionRequestDTO dto) {
        try {
            return ResponseEntity.ok(clasificacionService.actualizarEstadisticas(jugadorId, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            clasificacionService.eliminarClasificacion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}