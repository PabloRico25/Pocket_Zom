package com.example.billetera.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "perfil")
public interface PerfilClient {
    @GetMapping("/api/jugadores/{id}")
    boolean existeJugador(@PathVariable("id") Long id);
}