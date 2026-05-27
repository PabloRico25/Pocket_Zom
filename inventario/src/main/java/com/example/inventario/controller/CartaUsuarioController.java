package com.example.inventario.controller;

import com.example.inventario.dto.CartaUsuarioDTO;
import com.example.inventario.service.CartaUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario/cartas")
@RequiredArgsConstructor
public class CartaUsuarioController {
    private final CartaUsuarioService cartaUsuarioService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<CartaUsuarioDTO> agregar(@PathVariable Long jugadorId,
                                                   @Valid @RequestBody CartaUsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaUsuarioService.agregarCarta(jugadorId, dto));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{jugadorId}/{codigoCarta}")
    public ResponseEntity<Void> quitar(@PathVariable Long jugadorId,
                                       @PathVariable String codigoCarta,
                                       @RequestParam Integer cantidad) {
        try {
            cartaUsuarioService.quitarCarta(jugadorId, codigoCarta, cantidad);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<CartaUsuarioDTO>> listar(@PathVariable Long jugadorId) {
        try {
            return ResponseEntity.ok(cartaUsuarioService.listarCartas(jugadorId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tiene")
    public ResponseEntity<Boolean> tieneCarta(@RequestParam Long jugadorId,
                                              @RequestParam String codigoCarta,
                                              @RequestParam(required = false) Integer cantidad) {
        try {
            boolean resultado = cartaUsuarioService.tieneCarta(jugadorId, codigoCarta, cantidad);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false);
        }
    }
}