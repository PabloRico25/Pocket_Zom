package com.example.inventario.controller;

import com.example.inventario.dto.CartaUsuarioDTO;
import com.example.inventario.model.CartaUsuario;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.CartaUsuarioRepository;
import com.example.inventario.service.CartaUsuarioService;
import com.example.inventario.service.InventarioService;
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
    private final InventarioService inventarioService;
    private final CartaUsuarioRepository cartaUsuarioRepository;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<CartaUsuarioDTO> agregar(@PathVariable Long jugadorId,
                                                   @Valid @RequestBody CartaUsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaUsuarioService.agregarCarta(jugadorId, dto));
        } catch (RuntimeException e) {
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

    @PostMapping("/transferir")
    public ResponseEntity<Void> transferir(@RequestParam Long jugadorOrigenId,
                                           @RequestParam Long jugadorDestinoId,
                                           @RequestParam String codigoCarta,
                                           @RequestParam(defaultValue = "1") Integer cantidad) {
        cartaUsuarioService.transferirCarta(jugadorOrigenId, jugadorDestinoId, codigoCarta, cantidad);
        return ResponseEntity.ok().build();
    }

    // ========== NUEVO ENDPOINT para validar posesión de carta ==========
    @GetMapping("/tiene")
    public ResponseEntity<Boolean> tieneCarta(@RequestParam Long jugadorId,
                                              @RequestParam String codigoCarta,
                                              @RequestParam(required = false) Integer cantidad) {
        try {
            Inventario inventario = inventarioService.obtenerEntidad(jugadorId);
            String codigoNormalizado = codigoCarta.trim().toUpperCase();
            CartaUsuario carta = cartaUsuarioRepository.findByInventarioIdAndCodigoCarta(inventario.getId(), codigoNormalizado)
                    .orElse(null);
            if (carta == null) {
                return ResponseEntity.ok(false);
            }
            int needed = cantidad != null ? cantidad : 1;
            return ResponseEntity.ok(carta.getCantidad() >= needed);
        } catch (RuntimeException e) {
            // Si el inventario no existe, el jugador no tiene la carta
            return ResponseEntity.ok(false);
        }
    }
}