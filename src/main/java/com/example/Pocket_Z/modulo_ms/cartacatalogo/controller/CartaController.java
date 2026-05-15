package com.example.Pocket_Z.modulo_ms.cartacatalogo.controller;

import com.example.Pocket_Z.modulo_ms.cartacatalogo.model.Carta;
import com.example.Pocket_Z.modulo_ms.cartacatalogo.services.CartaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartas")
@RequiredArgsConstructor
public class CartaController {
    private final CartaService cartaService;

    @GetMapping
    public List<Carta> listar() {
        return cartaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carta> obtener(@PathVariable Long id) {
        return cartaService.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carta crear(@RequestBody Carta carta) {
        return cartaService.guardar(carta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carta> actualizar(@PathVariable Long id, @RequestBody Carta carta) {
        if (cartaService.obtener(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        carta.setId(id);
        return ResponseEntity.ok(cartaService.guardar(carta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (cartaService.obtener(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cartaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}