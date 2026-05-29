package com.example.compra.controller;

import com.example.compra.dto.SuministroDTO;
import com.example.compra.service.SuministroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suministros")
@RequiredArgsConstructor
public class SuministroController {

    private final SuministroService suministroService;
    @GetMapping
    public ResponseEntity<List<SuministroDTO>> listar() {
        List<SuministroDTO> lista = suministroService.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuministroDTO> obtener(@PathVariable Long id) {
        SuministroDTO dto = suministroService.obtener(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<SuministroDTO> crear(@Valid @RequestBody SuministroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(suministroService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuministroDTO> actualizar(@PathVariable Long id, @Valid @RequestBody SuministroDTO dto) {
        SuministroDTO actualizado = suministroService.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        suministroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
