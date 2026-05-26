package com.example.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario")
public interface InventarioClient {
    @PostMapping("/api/v1/inventario/cartas/{jugadorId}/agregar")
    void agregarCarta(@RequestParam Long jugadorId,
                      @RequestParam String codigoCarta,
                      @RequestParam Integer cantidad);
}
