package com.example.logros.controller;

import com.example.logros.dto.LogroJugadorRequestDTO;
import com.example.logros.dto.LogroJugadorResponseDTO;
import com.example.logros.service.LogroJugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logros-jugador")
@RequiredArgsConstructor
public class LogroJugadorController {
    private final LogroJugadorService logroJugadorService;

    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<LogroJugadorResponseDTO>> listarPorJugador(@PathVariable String jugadorId) {
        List<LogroJugadorResponseDTO> list = logroJugadorService.listarPorJugador(jugadorId);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @PostMapping("/desbloquear")
    public ResponseEntity<LogroJugadorResponseDTO> desbloquear(@Valid @RequestBody LogroJugadorRequestDTO dto) {
        try {
            LogroJugadorResponseDTO nuevo = logroJugadorService.desbloquear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/verificar/{jugadorId}/{tipo}/{valor}")
    public ResponseEntity<List<LogroJugadorResponseDTO>> verificarYDesbloquear(
            @PathVariable String jugadorId,
            @PathVariable String tipo,
            @PathVariable Integer valor) {
        List<LogroJugadorResponseDTO> nuevos = logroJugadorService.verificarYDesbloquear(jugadorId, tipo, valor);
        return ResponseEntity.ok(nuevos);
    }
}