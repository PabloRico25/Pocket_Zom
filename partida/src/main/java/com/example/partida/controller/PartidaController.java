package com.example.partida.controller;

import com.example.partida.dto.FinalizarPartidaDTO;
import com.example.partida.dto.PartidaDTO;
import com.example.partida.service.PartidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partidas")
public class PartidaController {
    @Autowired
    private PartidaService partidaService;
    @GetMapping
    @Operation(summary = "Listar partidas", description = "Obtiene una lista de todas las partidas finalizadas o en proceso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partidas listadas correctamente")
    })
    public ResponseEntity<List<PartidaDTO>> listarTodas() {
        return ResponseEntity.ok(partidaService.listarTodas());
    }

    @GetMapping("/jugador/{jugadorId}")
    @Operation(summary = "Listar partidas por jugador", description = "Obtiene una lista de todas las partidas donde participa el jugador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partidas listadas por jugador correctamente")
    })
    public ResponseEntity<List<PartidaDTO>> listarPorJugador(@Parameter(description = "ID del jugador", required = true) @PathVariable Long jugadorId) {
        return ResponseEntity.ok(partidaService.listarPorJugador(jugadorId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca partida", description = "Obtiene informacion sobre una partida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partida encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Partida no existe")
    })
    public ResponseEntity<PartidaDTO> obtener(@Parameter(description = "ID de la partida", required = true) @PathVariable Long id) {
        try {
            return ResponseEntity.ok(partidaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea partida", description = "Crear una partida entre dos jugadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partida creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear partida")
    })
    public ResponseEntity<PartidaDTO> crear(@Valid @RequestBody PartidaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.crearPartida(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar partida", description = "Termina una partida indicando el ganador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partida finalizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al finalizar partida")
    })
    public ResponseEntity<PartidaDTO> finalizar(@Parameter(description = "ID de la partida", required = true) @PathVariable Long id,@Valid @RequestBody FinalizarPartidaDTO dto) {
        try {
            return ResponseEntity.ok(partidaService.finalizarPartida(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar partida", description = "Borra el registro de una partida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partida borrada correctamente"),
            @ApiResponse(responseCode = "400", description = "No existe la partida")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la partida", required = true) @PathVariable Long id) {
        try {
            partidaService.eliminarPartida(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
