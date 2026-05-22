package com.example.inventario.controller;

import com.example.inventario.dto.CartaUsuarioRequestDTO;
import com.example.inventario.dto.CartaUsuarioResponseDTO;
import com.example.inventario.service.CartaUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inventario/cartas")
@RequiredArgsConstructor
public class CartaUsuarioController {
    private final CartaUsuarioService cartaUsuarioService;

    @PostMapping("/{jugadorId}")
    public ResponseEntity<CartaUsuarioResponseDTO> agregar(@PathVariable Long jugadorId,
                                                           @Valid @RequestBody CartaUsuarioRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(cartaUsuarioService.agregarCarta(jugadorId, dto));
        } catch (RuntimeException e) {
            log.error("Error al agregar carta: {}", e.getMessage());
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
    public ResponseEntity<List<CartaUsuarioResponseDTO>> listar(@PathVariable Long jugadorId) {
        try {
            return ResponseEntity.ok(cartaUsuarioService.listarCartas(jugadorId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}