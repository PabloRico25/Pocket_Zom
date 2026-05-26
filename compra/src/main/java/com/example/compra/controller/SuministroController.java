package com.example.compra.controller;

import com.example.compra.dto.SuministroDTO;
import com.example.compra.service.SuministroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suministros")
public class SuministroController {
    @Autowired
    private SuministroService suministroService;

    @GetMapping
    public ResponseEntity<List<SuministroDTO>> listar() {
        return ResponseEntity.ok(suministroService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuministroDTO> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(suministroService.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SuministroDTO> crear(@Valid @RequestBody SuministroDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(suministroService.crear(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuministroDTO> actualizar(@PathVariable Long id,
                                                    @Valid @RequestBody SuministroDTO dto) {
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
