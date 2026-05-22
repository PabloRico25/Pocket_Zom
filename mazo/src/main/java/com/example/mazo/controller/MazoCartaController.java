package com.example.mazo.controller;

import com.example.mazo.dto.MazoCartaRequestDTO;
import com.example.mazo.dto.MazoCartaResponseDTO;
import com.example.mazo.service.MazoCartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mazos/{mazoId}/cartas")
@RequiredArgsConstructor
public class MazoCartaController {
    private final MazoCartaService mazoCartaService;

    @GetMapping
    public ResponseEntity<List<MazoCartaResponseDTO>> listar(@PathVariable Long mazoId) {
        List<MazoCartaResponseDTO> cartas = mazoCartaService.listarCartasDeMazo(mazoId);
        if (cartas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cartas);
    }

    @PostMapping
    public ResponseEntity<MazoCartaResponseDTO> agregar(@PathVariable Long mazoId,
                                                        @Valid @RequestBody MazoCartaRequestDTO dto) {
        try {
            MazoCartaResponseDTO nueva = mazoCartaService.agregarCarta(mazoId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            log.error("Error al agregar carta al mazo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{codigoCarta}")
    public ResponseEntity<Void> quitar(@PathVariable Long mazoId,
                                       @PathVariable String codigoCarta,
                                       @RequestParam Integer cantidad) {
        try {
            mazoCartaService.quitarCarta(mazoId, codigoCarta, cantidad);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/limpiar")
    public ResponseEntity<Void> limpiar(@PathVariable Long mazoId) {
        mazoCartaService.limpiarMazo(mazoId);
        return ResponseEntity.noContent().build();
    }
}