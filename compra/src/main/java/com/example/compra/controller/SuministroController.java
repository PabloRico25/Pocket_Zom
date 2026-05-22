package com.example.compra.controller;

import com.example.compra.dto.SuministroRequestDTO;
import com.example.compra.dto.SuministroResponseDTO;
import com.example.compra.service.SuministroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suministros")
@RequiredArgsConstructor
public class SuministroController {
    private final SuministroService suministroService;

    @GetMapping
    public ResponseEntity<List<SuministroResponseDTO>> listar() {
        List<SuministroResponseDTO> list = suministroService.listar();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuministroResponseDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(suministroService.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SuministroResponseDTO> crear(@Valid @RequestBody SuministroRequestDTO dto) {
        try {
            SuministroResponseDTO nuevo = suministroService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuministroResponseDTO> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody SuministroRequestDTO dto) {
        try {
            return ResponseEntity.ok(suministroService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            suministroService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}