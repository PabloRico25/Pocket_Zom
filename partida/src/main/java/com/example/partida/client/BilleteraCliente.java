package com.example.partida.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "billetera")
public interface BilleteraCliente {

    @PostMapping("/api/v1/movimientos/{idJugador}")
    void registrarMovimiento(@PathVariable("idJugador") Long idJugador,@RequestBody Map<String, Object> body);
}
