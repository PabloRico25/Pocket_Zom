package com.example.inventario.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cartacatalogo")
public interface CartaClient {
    @GetMapping("/api/v1/cartas/codigo/{codigo}")
    Boolean existeCarta(@PathVariable("codigo") String codigo);
}