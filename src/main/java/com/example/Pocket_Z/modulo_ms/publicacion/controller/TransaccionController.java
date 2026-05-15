package com.example.Pocket_Z.modulo_ms.publicacion.controller;

import com.example.Pocket_Z.modulo_ms.publicacion.model.Transaccion;
import com.example.Pocket_Z.modulo_ms.publicacion.services.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {
    private final TransaccionService transaccionService;

    @PostMapping("/{publicacionId}/comprar/{compradorId}")
    public Transaccion comprar(@PathVariable Long publicacionId, @PathVariable Long compradorId) {
        return transaccionService.registrarCompra(publicacionId, compradorId);
    }

    @GetMapping("/comprador/{compradorId}")
    public List<Transaccion> listarCompras(@PathVariable Long compradorId) {
        return transaccionService.listarComprasPorComprador(compradorId);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public List<Transaccion> listarVentas(@PathVariable Long vendedorId) {
        return transaccionService.listarVentasPorVendedor(vendedorId);
    }
}