package com.example.logros.controller;

import com.example.logros.dto.LogroJugadorDTO;
import com.example.logros.service.LogroJugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logros-jugador")
public class LogroJugadorController {
    @Autowired
    private LogroJugadorService logroJugadorService;

    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<LogroJugadorDTO>> listarPorJugador(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(logroJugadorService.listarPorJugador(jugadorId));
    }

    @PostMapping("/{jugadorId}/{idLogro}")
    public ResponseEntity<LogroJugadorDTO> desbloquear(@PathVariable Long jugadorId,
                                                       @PathVariable String idLogro) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logroJugadorService.desbloquear(jugadorId, idLogro));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/verificar/{jugadorId}/{tipo}/{valor}")
    public ResponseEntity<List<LogroJugadorDTO>> verificar(@PathVariable Long jugadorId,
                                                           @PathVariable String tipo,
                                                           @PathVariable Integer valor) {
        return ResponseEntity.ok(logroJugadorService.verificarYDesbloquear(jugadorId, tipo, valor));
    }
}
