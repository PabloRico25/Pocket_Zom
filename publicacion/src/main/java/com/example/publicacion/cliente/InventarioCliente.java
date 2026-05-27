package com.example.publicacion.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario")
public interface InventarioCliente {
    @PostMapping("/api/v1/inventario/cartas/{idJugador}/transferir")
    void transferirCarta(@RequestParam Long idJugadorOrigen,
                         @RequestParam Long idJugadorDestino,
                         @RequestParam String codigoCarta,
                         @RequestParam Integer cantidad);
}
