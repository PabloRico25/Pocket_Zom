package com.example.billetera.controller;

import com.example.billetera.dto.CarteraResponseDTO;
import com.example.billetera.service.CarteraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/carteras")
@RequiredArgsConstructor
public class CarteraController {
    private final CarteraService carteraService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<CarteraResponseDTO> crear(@PathVariable Long jugadorId) {
        try {
            CarteraResponseDTO response = carteraService.toDTO(carteraService.crearCartera(jugadorId));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            log.warn("Error al crear cartera: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<CarteraResponseDTO> obtener(@PathVariable Long jugadorId) {
        return carteraService.obtenerPorJugador(jugadorId)
                .map(c -> ResponseEntity.ok(carteraService.toDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }
}