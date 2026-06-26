package com.example.logros.controller;

import com.example.logros.dto.LogroJugadorDTO;
import com.example.logros.service.LogroJugadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logros-jugador")
@Tag(name = "Logro-Jugador", description = "Operaciones relacionadas con la asignacion y conexion entre jugador y logros")
public class LogroJugadorController {
    @Autowired
    private LogroJugadorService logroJugadorService;

    @GetMapping("/{jugadorId}")
    @Operation(summary = "Listar logros de un jugador", description = "Obtiene la lista de los logros de un jugador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al listar logros")
    })
    public ResponseEntity<List<LogroJugadorDTO>> listarPorJugador(@Parameter(description = "ID del jugador", required = true) @PathVariable Long jugadorId) {
        return ResponseEntity.ok(logroJugadorService.listarPorJugador(jugadorId));
    }

    @PostMapping("/{jugadorId}/{idLogro}")
    @Operation(summary = "Asignar un logro", description = "Asigna un lorgo a un jugador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logro asignado correctamente correctamente"),
            @ApiResponse(responseCode = "409", description = "Error al asignar el logro")
    })
    public ResponseEntity<LogroJugadorDTO> desbloquear(@Parameter(description = "ID del jugador", required = true) @PathVariable Long jugadorId,
                                                       @Parameter(description = "ID del logro", required = true) @PathVariable String idLogro) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logroJugadorService.desbloquear(jugadorId, idLogro));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/verificar/{jugadorId}/{tipo}/{valor}")
    @Operation(summary = "Verificar logros con filtro", description = "Obtiene la lista de logros de un jugador que cumplan con un tipo y valor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de logros generado correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay logros a listar")
    })
    public ResponseEntity<List<LogroJugadorDTO>> verificar(@Parameter(description = "ID del jugador", required = true) @PathVariable Long jugadorId,
                                                           @Parameter(description = "Tipo de logro", required = true) @PathVariable String tipo,
                                                           @Parameter(description = "Valor de logro", required = true) @PathVariable Integer valor) {
        return ResponseEntity.ok(logroJugadorService.verificarYDesbloquear(jugadorId, tipo, valor));
    }
}
