package com.example.logros.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "partida")
public interface PartidaClient {
    @GetMapping("/api/v1/partidas/jugador/{jugadorId}/victorias")
    Integer getVictorias(@PathVariable("jugadorId") Long jugadorId);
}
