package com.example.inventario.controller;

import com.example.inventario.model.Inventario;
import com.example.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventarios")
@RequiredArgsConstructor
@Tag(name = "Inventarios", description = "Operaciones relacionadas con los inventarios de los jugadores")
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping("/{idJugador}")
    @Operation(summary = "Buscar inventario por jugador", description = "Obtiene el inventario segun el ID del jugador ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "El jugador no tiene inventario registrado")
    })
    public ResponseEntity<Inventario> buscar(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        Inventario inv = inventarioService.buscarPorJugador(idJugador);
        if (inv == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inv);
    }

    @PostMapping("/{idJugador}")
    @Operation(summary = "Crear inventario", description = "Crea un nuevo inventario para el jugador segun su ID, solo se puede tener un inventario por jugador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario creado correctamente"),
            @ApiResponse(responseCode = "409", description = "El jugador ya tiene inventario registrado")
    })
    public ResponseEntity<Inventario> crear(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        Inventario nuevo = inventarioService.crear(idJugador);
        if (nuevo == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}