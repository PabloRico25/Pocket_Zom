package com.example.Pocket_Z.modulo_ms.billetera.controller;

import com.example.Pocket_Z.modulo_ms.billetera.model.Movimiento;
import com.example.Pocket_Z.modulo_ms.billetera.services.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
    private final MovimientoService movimientoService;

    @GetMapping("/{jugadorId}")
    public List<Movimiento> listar(@PathVariable Long jugadorId) {
        return movimientoService.listarMovimientos(jugadorId);
    }

    @PostMapping("/{jugadorId}")
    public Movimiento registrar(@PathVariable Long jugadorId,
                                @RequestParam String tipo,
                                @RequestParam Integer monto,
                                @RequestParam String concepto) {
        return movimientoService.registrarMovimiento(jugadorId, tipo, monto, concepto);
    }
}