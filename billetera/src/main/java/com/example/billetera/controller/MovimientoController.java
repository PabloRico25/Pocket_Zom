package com.example.billetera.controller;

import com.example.billetera.model.Movimiento;
import com.example.billetera.service.MovimientoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private PartidaClient partidaClient; // opcional, ejemplo de Feign

    @PostMapping("/{jugadorId}")
    public ResponseEntity<MovimientoDTO> registrar(@PathVariable Long jugadorId,
                                                   @RequestParam String tipo,
                                                   @RequestParam Integer monto,
                                                   @RequestParam String concepto) {
        log.info("POST /api/movimientos/" + jugadorId);

        if (tipo == null || (!"INGRESO".equalsIgnoreCase(tipo) && !"EGRESO".equalsIgnoreCase(tipo))) {
            log.info("Tipo inválido: " + tipo);
            return ResponseEntity.badRequest().build();
        }

        if (monto == null || monto <= 0) {
            log.info("Monto inválido: " + monto);
            return ResponseEntity.badRequest().build();
        }

        if (concepto == null || concepto.trim().isEmpty()) {
            log.info("Concepto vacío");
            return ResponseEntity.badRequest().build();
        }

        try {
            boolean saldoValido = partidaClient.validarSaldo(jugadorId, monto);
            if (!saldoValido && "EGRESO".equalsIgnoreCase(tipo)) {
                log.info("Saldo insuficiente según microservicio partida");
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            log.info("Error al validar con partida: " + e.getMessage());
        }

        Movimiento movimiento = movimientoService.registrarMovimiento(jugadorId, tipo, monto, concepto);

        if (movimiento == null) {
            return ResponseEntity.badRequest().build();
        }

        MovimientoDTO dto = new MovimientoDTO();
        dto.setIdTransaccion(movimiento.getIdTransaccion());
        dto.setTipo(movimiento.getTipo());
        dto.setMonto(movimiento.getMonto());
        dto.setConcepto(movimiento.getConcepto());
        dto.setFecha(movimiento.getFecha());

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<List<MovimientoDTO>> listar(@PathVariable Long jugadorId) {
        log.info("GET /api/movimientos/" + jugadorId);

        List<Movimiento> movimientos = movimientoService.listarMovimientos(jugadorId);

        if (movimientos == null || movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<MovimientoDTO> resultado = new ArrayList<>();
        for (Movimiento m : movimientos) {
            MovimientoDTO dto = new MovimientoDTO();
            dto.setIdTransaccion(m.getIdTransaccion());
            dto.setTipo(m.getTipo());
            dto.setMonto(m.getMonto());
            dto.setConcepto(m.getConcepto());
            dto.setFecha(m.getFecha());
            resultado.add(dto);
        }

        return ResponseEntity.ok(resultado);
    }
}