package com.example.billetera.controller;

import com.example.billetera.model.Cartera;
import com.example.billetera.service.CarteraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carteras")
@RequiredArgsConstructor
public class CarteraController {

    private final CarteraService carteraService;

    @GetMapping
    public ResponseEntity<List<Cartera>> listar() {
        List<Cartera> lista = carteraService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idJugador}")
    public ResponseEntity<Cartera> buscarPorJugador(@PathVariable Long idJugador) {
        Cartera cartera = carteraService.buscarPorJugador(idJugador);
        if (cartera == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartera);
    }

    // Crea una cartera para un jugador
    @PostMapping("/{idJugador}")
    public ResponseEntity<Cartera> crear(@PathVariable Long idJugador) {
        Cartera nueva = carteraService.crear(idJugador);
        if (nueva == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
}
