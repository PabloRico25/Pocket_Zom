package com.example.compra.controller;

import com.example.compra.dto.AbrirSobreDTO;
import com.example.compra.dto.AperturaDTO;
import com.example.compra.service.AperturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aperturas")
public class AperturaController {
    @Autowired
    private AperturaService aperturaService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<AperturaDTO> abrir(@PathVariable Long jugadorId,
                                             @Valid @RequestBody AbrirSobreDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(aperturaService.abrirSuministro(jugadorId, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<AperturaDTO>> listar(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(aperturaService.listarAperturasPorJugador(jugadorId));
    }
}
