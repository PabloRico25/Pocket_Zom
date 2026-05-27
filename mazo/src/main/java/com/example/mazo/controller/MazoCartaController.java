package com.example.mazo.controller;

import com.example.mazo.dto.MazoCartaDTO;
import com.example.mazo.service.MazoCartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mazos/{mazoId}/cartas")
@RequiredArgsConstructor
public class MazoCartaController {
    private final MazoCartaService mazoCartaService;
    @GetMapping
    public ResponseEntity<List<MazoCartaDTO>> listar(@PathVariable Long mazoId) {
        return ResponseEntity.ok(mazoCartaService.listarCartas(mazoId));
    }
    @PostMapping
    public ResponseEntity<MazoCartaDTO> agregar(@PathVariable Long mazoId, @Valid @RequestBody MazoCartaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(mazoCartaService.agregarCarta(mazoId, dto));
        } catch (RuntimeException e) {
            e.printStackTrace();
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