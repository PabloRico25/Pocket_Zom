package com.example.compra.controller;

import com.example.compra.dto.AbrirSobreDTO;
import com.example.compra.dto.AperturaDTO;
import com.example.compra.service.AperturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aperturas")
@RequiredArgsConstructor
public class AperturaController {

    private final AperturaService aperturaService;
    @GetMapping("/{idJugador}")
    public ResponseEntity<List<AperturaDTO>> listar(@PathVariable Long idJugador) {
        return ResponseEntity.ok(aperturaService.listarPorJugador(idJugador));
    }
    @PostMapping("/{idJugador}")
    public ResponseEntity<AperturaDTO> abrir(@PathVariable Long idJugador, @Valid @RequestBody AbrirSobreDTO dto) {
        AperturaDTO resultado = aperturaService.abrir(idJugador, dto);
        if (resultado == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
