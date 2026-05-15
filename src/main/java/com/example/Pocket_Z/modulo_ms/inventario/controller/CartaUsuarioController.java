package com.example.Pocket_Z.modulo_ms.inventario.controller;

import com.example.Pocket_Z.modulo_ms.inventario.model.CartaUsuario;
import com.example.Pocket_Z.modulo_ms.inventario.services.CartaUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartas-usuario")
@RequiredArgsConstructor
public class CartaUsuarioController {
    private final CartaUsuarioService cartaUsuarioService;

    @GetMapping("/jugador/{jugadorId}")
    public List<CartaUsuario> listarPorJugador(@PathVariable Long jugadorId) {
        return cartaUsuarioService.listarPorJugador(jugadorId);
    }

    @PostMapping("/jugador/{jugadorId}/agregar")
    public CartaUsuario agregarCarta(@PathVariable Long jugadorId,
                                     @RequestParam String codigoCarta,
                                     @RequestParam int cantidad) {
        return cartaUsuarioService.agregarCarta(jugadorId, codigoCarta, cantidad);
    }

    @PatchMapping("/{id}/favorita")
    public ResponseEntity<Void> marcarFavorita(@PathVariable Long id, @RequestParam boolean favorita) {
        cartaUsuarioService.marcarFavorita(id, favorita);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cartaUsuarioService.eliminarCarta(id);
        return ResponseEntity.noContent().build();
    }
}