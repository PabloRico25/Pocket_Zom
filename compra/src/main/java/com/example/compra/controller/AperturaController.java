package com.example.compra.controller;

import com.example.compra.dto.AperturaRequestDTO;
import com.example.compra.dto.AperturaResponseDTO;
import com.example.compra.service.AperturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aperturas")
@RequiredArgsConstructor
public class AperturaController {
    private final AperturaService aperturaService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<AperturaResponseDTO> abrir(@PathVariable Long jugadorId,
                                                     @Valid @RequestBody AperturaRequestDTO dto) {
        try {
            AperturaResponseDTO apertura = aperturaService.abrirSuministro(jugadorId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(apertura);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<AperturaResponseDTO>> listar(@PathVariable Long jugadorId) {
        List<AperturaResponseDTO> list = aperturaService.listarAperturasPorJugador(jugadorId);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }
}