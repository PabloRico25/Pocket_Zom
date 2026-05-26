package com.example.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "billetera")
public interface BilleteraClient {
    @PostMapping("/api/v1/movimientos/{jugadorId}")
    void registrarMovimiento(@RequestParam Long jugadorId,
                             @RequestParam String tipo,
                             @RequestParam Integer monto,
                             @RequestParam String concepto);
}
