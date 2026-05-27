package com.example.partida.controller;

import com.example.partida.dto.FinalizarPartidaDTO;
import com.example.partida.dto.PartidaDTO;
import com.example.partida.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partidas")
public class PartidaController {
    @Autowired
    private PartidaService partidaService;
    @GetMapping
    public ResponseEntity<List<PartidaDTO>> listarTodas() {
        return ResponseEntity.ok(partidaService.listarTodas());
    }
    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<PartidaDTO>> listarPorJugador(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(partidaService.listarPorJugador(jugadorId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PartidaDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(partidaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<PartidaDTO> crear(@Valid @RequestBody PartidaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.crearPartida(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<PartidaDTO> finalizar(@PathVariable Long id,
                                                @Valid @RequestBody FinalizarPartidaDTO dto) {
        try {
            return ResponseEntity.ok(partidaService.finalizarPartida(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            partidaService.eliminarPartida(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
