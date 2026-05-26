package com.example.billetera.controller;

import com.example.billetera.dto.CarteraDTO;
import com.example.billetera.service.CarteraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carteras")
@RequiredArgsConstructor
public class CarteraController {
    private final CarteraService carteraService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<CarteraDTO> crear(@PathVariable Long jugadorId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(carteraService.crearCartera(jugadorId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<CarteraDTO> obtener(@PathVariable Long jugadorId) {
        CarteraDTO dto = carteraService.obtenerPorJugador(jugadorId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
}