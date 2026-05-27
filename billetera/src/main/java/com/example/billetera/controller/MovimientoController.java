package com.example.billetera.controller;

import com.example.billetera.dto.MovimientoDTO;
import com.example.billetera.model.Movimiento;
import com.example.billetera.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping("/{idJugador}")
    public ResponseEntity<List<Movimiento>> listar(@PathVariable Long idJugador) {
        List<Movimiento> lista = movimientoService.listarPorJugador(idJugador);
        if (lista == null) return ResponseEntity.notFound().build();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // Consumido por Postman y por otros MS via Feign
    @PostMapping("/{idJugador}")
    public ResponseEntity<Movimiento> registrar(@PathVariable Long idJugador,
                                                @Valid @RequestBody MovimientoDTO dto) {
        Movimiento resultado = movimientoService.registrar(idJugador, dto.getTipo(), dto.getMonto(), dto.getConcepto());
        if (resultado == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
