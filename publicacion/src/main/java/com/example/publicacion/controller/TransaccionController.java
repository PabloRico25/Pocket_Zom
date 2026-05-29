package com.example.publicacion.controller;

import com.example.publicacion.dto.CompraDTO;
import com.example.publicacion.dto.TransaccionDTO;
import com.example.publicacion.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transacciones")
@RequiredArgsConstructor
public class TransaccionController {
    private final TransaccionService transaccionService;

    @PostMapping("/{compradorId}")
    public ResponseEntity<TransaccionDTO> comprar(@PathVariable Long compradorId,@Valid @RequestBody CompraDTO dto) {
        TransaccionDTO resultado = transaccionService.comprar(compradorId, dto.getPublicacionId());
        if (resultado == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<TransaccionDTO>> listarCompras(@PathVariable Long compradorId) {
        return ResponseEntity.ok(transaccionService.listarPorComprador(compradorId));
    }
}
