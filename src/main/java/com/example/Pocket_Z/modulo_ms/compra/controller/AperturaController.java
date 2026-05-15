package com.example.Pocket_Z.modulo_ms.compra.controller;

import com.example.Pocket_Z.modulo_ms.compra.model.Apertura;
import com.example.Pocket_Z.modulo_ms.compra.services.AperturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aperturas")
@RequiredArgsConstructor
public class AperturaController {
    private final AperturaService aperturaService;

    @PostMapping("/{jugadorId}/abrir/{suministroId}")
    public Apertura abrir(@PathVariable Long jugadorId, @PathVariable Long suministroId) {
        return aperturaService.abrirSuministro(jugadorId, suministroId);
    }

    @GetMapping("/{jugadorId}")
    public List<Apertura> listarPorJugador(@PathVariable Long jugadorId) {
        return aperturaService.listarAperturasPorJugador(jugadorId);
    }
}