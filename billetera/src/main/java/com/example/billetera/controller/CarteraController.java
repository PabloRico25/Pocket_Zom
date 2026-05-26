package com.example.billetera.controller;

import com.example.billetera.Cliente.RangoClient;
import com.example.billetera.dto.CarteraDTO;
import com.example.billetera.dto.CarteraResponseDTO;
import com.example.billetera.model.Cartera;
import com.example.billetera.service.CarteraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/carteras")
public class CarteraController {
    @Autowired
    private CarteraService carteraService;

    private RangoClient rangoClient;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<CarteraDTO> crear(@PathVariable Long jugadorId) {
        log.info("Creando cartera para jugador: " + jugadorId);

        Cartera cartera = carteraService.crearCartera(jugadorId);

        if (cartera == null) {
            log.info("Error: ya existe cartera para jugador " + jugadorId);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Notificar a otro microservicio
        try {
            rangoClient.notificarNuevaCartera(jugadorId);
        } catch (Exception e) {
            log.info("No se pudo notificar a ranking: " + e.getMessage());
        }

        CarteraDTO dto = new CarteraDTO();
        dto.setId(cartera.getId());
        dto.setJugadorId(cartera.getJugadorId());
        dto.setSaldo(cartera.getSaldo());

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<CarteraDTO> obtener(@PathVariable Long jugadorId) {
        Cartera cartera = carteraService.obtenerPorJugador(jugadorId);

        if (cartera == null) {
            return ResponseEntity.notFound().build();
        }

        CarteraDTO dto = new CarteraDTO();
        dto.setId(cartera.getId());
        dto.setJugadorId(cartera.getJugadorId());
        dto.setSaldo(cartera.getSaldo());

        return ResponseEntity.ok(dto);
    }
}