package com.example.inventario.controller;

import com.example.inventario.dto.AgregarCartaDTO;
import com.example.inventario.dto.TransferirCartaDTO;
import com.example.inventario.model.CartaUsuario;
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

    @GetMapping("/{idJugador}")
    public ResponseEntity<List<CartaUsuario>> listar(@PathVariable Long idJugador) {
        List<CartaUsuario> lista = cartaUsuarioService.listarCartas(idJugador);
        if (lista == null) return ResponseEntity.notFound().build();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // Consumido por mazo via Feign — mantiene @RequestParam
    @GetMapping("/tiene")
    public ResponseEntity<Boolean> tieneCarta(@RequestParam Long idJugador,
                                              @RequestParam String codigoCarta,
                                              @RequestParam(required = false) Integer cantidad) {
        return ResponseEntity.ok(cartaUsuarioService.tieneCarta(idJugador, codigoCarta, cantidad));
    }

    // Recibe JSON con codigoCarta y cantidad
    @PostMapping("/{idJugador}/agregar")
    public ResponseEntity<CartaUsuario> agregar(@PathVariable Long idJugador,
                                                @Valid @RequestBody AgregarCartaDTO dto) {
        CartaUsuario resultado = cartaUsuarioService.agregar(idJugador, dto.getCodigoCarta(), dto.getCantidad());
        if (resultado == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @DeleteMapping("/{idJugador}")
    public ResponseEntity<Void> quitar(@PathVariable Long idJugador,
                                       @RequestParam String codigoCarta,
                                       @RequestParam Integer cantidad) {
        boolean ok = cartaUsuarioService.quitar(idJugador, codigoCarta, cantidad);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // Consumido por publicacion via Feign — ahora con @RequestBody DTO
    @PostMapping("/transferir")
    public ResponseEntity<Void> transferir(@Valid @RequestBody TransferirCartaDTO dto) {
        boolean ok = cartaUsuarioService.transferir(
                dto.getIdJugadorOrigen(),
                dto.getIdJugadorDestino(),
                dto.getCodigoCarta(),
                dto.getCantidad()
        );
        if (!ok) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }
}

