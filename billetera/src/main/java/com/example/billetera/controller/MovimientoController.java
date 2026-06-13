package com.example.billetera.controller;

import com.example.billetera.dto.MovimientoDTO;
import com.example.billetera.model.Movimiento;
import com.example.billetera.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos", description = "Operaciones relacionadas con los movimientos de las carteras")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping("/{idJugador}")
    @Operation(summary = "Listar movimientos de un jugador", description = "Obtiene la lista de movimientos de la cartera del jugador, ordenados del mas reciente al mas antiguo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimientos encontrados correctamente"),
            @ApiResponse(responseCode = "204", description = "El jugador no tiene movimientos registrados"),
            @ApiResponse(responseCode = "404", description = "El jugador no tiene cartera registrada")
    })
    public ResponseEntity<List<Movimiento>> listar(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        List<Movimiento> lista = movimientoService.listarPorJugador(idJugador);
        if (lista == null) return ResponseEntity.notFound().build();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{idJugador}")
    @Operation(summary = "Registrar movimiento", description = "Registra un movimiento de tipo INGRESO o EGRESO en la cartera del jugador. El saldo no puede quedar negativo. El jugador debe existir en el microservicio perfil (validado via Feign)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "El jugador no existe, no tiene cartera o el saldo resultante seria negativo")
    })
    public ResponseEntity<Movimiento> registrar(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador,
            @Valid @RequestBody MovimientoDTO dto) {
        Movimiento resultado = movimientoService.registrar(idJugador, dto.getTipo(), dto.getMonto(), dto.getConcepto());
        if (resultado == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}