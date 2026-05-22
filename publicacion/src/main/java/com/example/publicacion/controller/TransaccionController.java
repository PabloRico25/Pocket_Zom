package com.example.publicacion.controller;

import com.example.publicacion.dto.CompraRequestDTO;
import com.example.publicacion.dto.TransaccionResponseDTO;
import com.example.publicacion.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {
    private final TransaccionService transaccionService;

    @PostMapping("/{compradorId}")
    public ResponseEntity<TransaccionResponseDTO> comprar(@PathVariable Long compradorId,
                                                          @Valid @RequestBody CompraRequestDTO dto) {
        try {
            TransaccionResponseDTO t = transaccionService.comprar(compradorId, dto.getPublicacionId());
            return ResponseEntity.status(HttpStatus.CREATED).body(t);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarCompras(@PathVariable Long compradorId) {
        List<TransaccionResponseDTO> list = transaccionService.listarComprasPorComprador(compradorId);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<TransaccionResponseDTO>> listarVentas(@PathVariable Long vendedorId) {
        List<TransaccionResponseDTO> list = transaccionService.listarVentasPorVendedor(vendedorId);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }
}