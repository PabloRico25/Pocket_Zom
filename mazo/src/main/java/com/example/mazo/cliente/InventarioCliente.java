package com.example.mazo.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario")
public interface InventarioCliente {
    @GetMapping("/api/v1/inventario/cartas/tiene")
    Boolean tieneCarta(@RequestParam Long idJugador,
                       @RequestParam String codigoCarta,
                       @RequestParam(required = false) Integer cantidad);
}