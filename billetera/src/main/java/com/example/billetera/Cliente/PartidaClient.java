package com.example.billetera.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "partida")
public interface PartidaClient {
    @PostMapping("/api/partidas/validar")
    boolean validarSaldo(@RequestParam("jugadorId") Long jugadorId,
                         @RequestParam("monto") Integer monto);
}
