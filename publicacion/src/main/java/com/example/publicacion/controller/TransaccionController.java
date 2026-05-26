package com.example.publicacion.controller;

import com.example.publicacion.dto.CompraDTO;
import com.example.publicacion.dto.TransaccionDTO;
import com.example.publicacion.service.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transacciones")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    @PostMapping("/{compradorId}")
    public ResponseEntity<TransaccionDTO> comprar(@PathVariable Long compradorId,
                                                  @Valid @RequestBody CompraDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccionService.comprar(compradorId, dto.getPublicacionId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<TransaccionDTO>> listarCompras(@PathVariable Long compradorId) {
        return ResponseEntity.ok(transaccionService.listarComprasPorComprador(compradorId));
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<TransaccionDTO>> listarVentas(@PathVariable Long vendedorId) {
        return ResponseEntity.ok(transaccionService.listarVentasPorVendedor(vendedorId));
    }
}
