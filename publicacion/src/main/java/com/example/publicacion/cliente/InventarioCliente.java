package com.example.publicacion.cliente;

import com.example.publicacion.dto.TransferirCartaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventario")
public interface InventarioCliente {

    @PostMapping("/api/v1/inventario/cartas/transferir")
    void transferirCarta(@RequestBody TransferirCartaDTO dto);
}