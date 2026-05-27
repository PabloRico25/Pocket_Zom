package com.example.logros.controller;

import com.example.logros.dto.LogroDTO;
import com.example.logros.service.LogroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logros")
public class LogroController {
    @Autowired
    private LogroService logroService;

    @GetMapping
    public ResponseEntity<List<LogroDTO>> listar() {
        return ResponseEntity.ok(logroService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogroDTO> obtener(@PathVariable String id) {
        try {
            return ResponseEntity.ok(logroService.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<LogroDTO>> listarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(logroService.listarPorTipo(tipo));
    }

    @PostMapping
    public ResponseEntity<LogroDTO> crear(@Valid @RequestBody LogroDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logroService.crear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogroDTO> actualizar(@PathVariable String id, @Valid @RequestBody LogroDTO dto) {
        try {
            return ResponseEntity.ok(logroService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        try {
            logroService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
