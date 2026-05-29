package com.example.partida.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rango")
public interface RangoCliente {
    @PutMapping("/api/v1/ranking/{jugadorId}")
    void actualizarRanking(@RequestParam Long jugadorId,@RequestParam boolean esVictoria,@RequestParam(required = false) Integer cambioElo);
}
