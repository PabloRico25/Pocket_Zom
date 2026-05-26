package com.example.billetera.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rango")
public interface RangoClient {
    @PostMapping("/api/ranking/notificar")
    void notificarNuevaCartera(@RequestParam("jugadorId") Long jugadorId);
}
