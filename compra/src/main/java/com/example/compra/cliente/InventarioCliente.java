package com.example.compra.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario")
public interface InventarioCliente {
    @PostMapping("/api/v1/inventario/cartas/{idJugador}/agregar")
    void agregarCarta(@PathVariable("idJugador") Long idJugador, @RequestParam String codigoCarta, @RequestParam Integer cantidad);
}

