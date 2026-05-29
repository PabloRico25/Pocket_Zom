package com.example.perfil.controller;

import com.example.perfil.model.Jugador;
import com.example.perfil.model.JugadorFaccion;
import com.example.perfil.service.JugadorFaccionService;
import com.example.perfil.service.JugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jugador-faccion")
@RequiredArgsConstructor
public class JugadorFaccionController {

    private final JugadorFaccionService jugadorFaccionService;

    @GetMapping("/{idJugador}")
    public ResponseEntity<List<JugadorFaccion>> listarPorJugador(@PathVariable Long idJugador) {
        List<JugadorFaccion> lista = jugadorFaccionService.listarPorJugador(idJugador);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{idJugador}/{idFaccion}")
    public ResponseEntity<JugadorFaccion> unir(@PathVariable Long idJugador, @PathVariable Long idFaccion) {
        JugadorFaccion resultado = jugadorFaccionService.unir(idJugador, idFaccion);
        if (resultado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @DeleteMapping("/{idJugador}/{idFaccion}")
    public ResponseEntity<Void> salir(@PathVariable Long idJugador, @PathVariable Long idFaccion) {
        boolean salio = jugadorFaccionService.salir(idJugador, idFaccion);
        if (!salio) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}