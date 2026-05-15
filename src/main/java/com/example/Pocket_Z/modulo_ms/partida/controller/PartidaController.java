package com.example.Pocket_Z.modulo_ms.partida.controller;

import com.example.Pocket_Z.modulo_ms.partida.model.Partida;
import com.example.Pocket_Z.modulo_ms.partida.services.PartidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
@RequiredArgsConstructor
public class PartidaController {
    private final PartidaService partidaService;

    @GetMapping
    public List<Partida> listar() {
        return partidaService.listar();
    }

    @GetMapping("/jugador/{jugadorId}")
    public List<Partida> listarPorJugador(@PathVariable Long jugadorId) {
        return partidaService.listarPorJugador(jugadorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> obtener(@PathVariable Long id) {
        return partidaService.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Partida crear(@RequestBody Partida partida) {
        return partidaService.crear(partida);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Partida> finalizar(@PathVariable Long id, @RequestParam Long ganadorId) {
        try {
            return ResponseEntity.ok(partidaService.finalizarPartida(id, ganadorId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        partidaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}