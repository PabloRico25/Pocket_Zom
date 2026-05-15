package com.example.Pocket_Z.modulo_ms.billetera.controller;

import com.example.Pocket_Z.modulo_ms.billetera.model.Cartera;
import com.example.Pocket_Z.modulo_ms.billetera.services.CarteraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carteras")
@RequiredArgsConstructor
public class CarteraController {
    private final CarteraService carteraService;

    @PostMapping("/{jugadorId}")
    public Cartera crear(@PathVariable Long jugadorId) {
        return carteraService.crearCartera(jugadorId);
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<Cartera> obtenerPorJugador(@PathVariable Long jugadorId) {
        return carteraService.obtenerPorJugador(jugadorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cartera> actualizar(@PathVariable Long id, @RequestBody Cartera cartera) {
        if (carteraService.obtenerPorJugador(cartera.getJugadorId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cartera.setId(id);
        return ResponseEntity.ok(carteraService.guardar(cartera));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carteraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}