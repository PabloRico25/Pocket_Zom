package com.example.perfil.controller;

import com.example.perfil.dto.JugadorRequestDTO;
import com.example.perfil.dto.JugadorResponseDTO;
import com.example.perfil.dto.LoginRequestDTO;
import com.example.perfil.dto.LoginResponseDTO;
import com.example.perfil.service.JugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class JugadorController {
    private final JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<List<JugadorResponseDTO>> listar() {
        List<JugadorResponseDTO> list = jugadorService.listar();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponseDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(jugadorService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<JugadorResponseDTO> registrar(@Valid @RequestBody JugadorRequestDTO dto) {
        try {
            JugadorResponseDTO nuevo = jugadorService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            return ResponseEntity.ok(jugadorService.login(request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}