package com.example.perfil.controller;

import com.example.perfil.dto.JugadorDTO;
import com.example.perfil.service.JugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jugadores")
@RequiredArgsConstructor
public class JugadorController {
    private final JugadorService jugadorService;

    @PostMapping("/registro")
    public ResponseEntity<JugadorDTO> registrar(@Valid @RequestBody JugadorDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.registrar(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JugadorDTO> login(@RequestBody JugadorDTO dto) {
        try {
            return ResponseEntity.ok(jugadorService.login(dto.getNombreUsuario(), dto.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existe(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.existeJugador(id));
    }

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> listarTodos() {
        return ResponseEntity.ok(jugadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(jugadorService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            jugadorService.eliminarJugador(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}