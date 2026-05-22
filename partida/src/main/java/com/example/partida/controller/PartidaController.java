package com.example.partida.controller;

import com.example.partida.dto.FinalizarPartidaRequestDTO;
import com.example.partida.dto.PartidaRequestDTO;
import com.example.partida.dto.PartidaResponseDTO;
import com.example.partida.service.PartidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/partidas")
@RequiredArgsConstructor
public class PartidaController {
    private final PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<PartidaResponseDTO>> listarTodas() {
        List<PartidaResponseDTO> partidas = partidaService.listarTodas();
        if (partidas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(partidas);
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<PartidaResponseDTO>> listarPorJugador(@PathVariable Long jugadorId) {
        List<PartidaResponseDTO> partidas = partidaService.listarPorJugador(jugadorId);
        if (partidas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(partidas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(partidaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{jugador1Id}")
    public ResponseEntity<PartidaResponseDTO> crear(@PathVariable Long jugador1Id,
                                                    @Valid @RequestBody PartidaRequestDTO dto) {
        try {
            PartidaResponseDTO nueva = partidaService.crearPartida(jugador1Id, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            log.error("Error al crear partida: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<PartidaResponseDTO> finalizar(@PathVariable Long id,
                                                        @Valid @RequestBody FinalizarPartidaRequestDTO dto) {
        try {
            return ResponseEntity.ok(partidaService.finalizarPartida(id, dto));
        } catch (RuntimeException e) {
            log.error("Error al finalizar partida: {}", e.getMessage());
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