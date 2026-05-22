package com.example.mazo.controller;

import com.example.mazo.dto.MazoRequestDTO;
import com.example.mazo.dto.MazoResponseDTO;
import com.example.mazo.service.MazoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mazos")
@RequiredArgsConstructor
public class MazoController {
    private final MazoService mazoService;

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<MazoResponseDTO>> listarPorJugador(@PathVariable Long jugadorId) {
        List<MazoResponseDTO> mazos = mazoService.listarPorJugador(jugadorId);
        if (mazos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(mazos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MazoResponseDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mazoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{jugadorId}")
    public ResponseEntity<MazoResponseDTO> crear(@PathVariable Long jugadorId,
                                                 @Valid @RequestBody MazoRequestDTO dto) {
        try {
            MazoResponseDTO nuevo = mazoService.crearMazo(jugadorId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            log.error("Error al crear mazo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MazoResponseDTO> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody MazoRequestDTO dto) {
        try {
            return ResponseEntity.ok(mazoService.actualizarMazo(id, dto));
        } catch (RuntimeException e) {
            log.error("Error al actualizar mazo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            mazoService.eliminarMazo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}