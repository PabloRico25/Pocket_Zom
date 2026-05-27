package com.example.mazo.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "perfil", url = "http://localhost:8091")
public interface PerfilClient {
    @GetMapping("/api/v1/jugadores/{id}/existe")
    Boolean existeJugador(@PathVariable("id") Long id);
}