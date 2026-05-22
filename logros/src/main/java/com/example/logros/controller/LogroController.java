package com.example.logros.controller;

import com.example.logros.dto.LogroRequestDTO;
import com.example.logros.dto.LogroResponseDTO;
import com.example.logros.service.LogroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logros")
@RequiredArgsConstructor
public class LogroController {
    private final LogroService logroService;

    @GetMapping
    public ResponseEntity<List<LogroResponseDTO>> listar() {
        List<LogroResponseDTO> list = logroService.listar();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogroResponseDTO> obtener(@PathVariable String id) {
        try {
            return ResponseEntity.ok(logroService.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<LogroResponseDTO>> listarPorTipo(@PathVariable String tipo) {
        List<LogroResponseDTO> list = logroService.listarPorTipo(tipo);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<LogroResponseDTO> crear(@Valid @RequestBody LogroRequestDTO dto) {
        try {
            LogroResponseDTO nuevo = logroService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogroResponseDTO> actualizar(@PathVariable String id,
                                                       @Valid @RequestBody LogroRequestDTO dto) {
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