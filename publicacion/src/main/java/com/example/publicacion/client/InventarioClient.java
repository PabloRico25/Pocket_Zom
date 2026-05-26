package com.example.publicacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario")
public interface InventarioClient {
    @PostMapping("/api/v1/inventario/cartas/{jugadorId}/transferir")
    void transferirCarta(@RequestParam Long jugadorOrigenId,
                         @RequestParam Long jugadorDestinoId,
                         @RequestParam String codigoCarta,
                         @RequestParam Integer cantidad);
}
