package com.example.mazo.controller;

import com.example.mazo.dto.MazoDTO;
import com.example.mazo.service.MazoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mazos")
@RequiredArgsConstructor
public class MazoController {
    private final MazoService mazoService;

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<MazoDTO>> listarPorJugador(@PathVariable Long jugadorId) {
        List<MazoDTO> mazos = mazoService.listarPorJugador(jugadorId);
        if (mazos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(mazos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MazoDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mazoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{jugadorId}")
    public ResponseEntity<MazoDTO> crear(@PathVariable Long jugadorId,
                                         @Valid @RequestBody MazoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(mazoService.crearMazo(jugadorId, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MazoDTO> actualizar(@PathVariable Long id,
                                              @Valid @RequestBody MazoDTO dto) {
        try {
            return ResponseEntity.ok(mazoService.actualizarMazo(id, dto));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrado")) return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
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