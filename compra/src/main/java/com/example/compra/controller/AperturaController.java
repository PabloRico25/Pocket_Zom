package com.example.compra.controller;

import com.example.compra.dto.AbrirSobreDTO;
import com.example.compra.dto.AperturaDTO;
import com.example.compra.service.AperturaService;
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
@RequestMapping("/api/v1/aperturas")
@RequiredArgsConstructor
@Tag(name = "Aperturas", description = "Operaciones relacionadas a la apertura de sobre y entrega de cartas aleatoria")
public class AperturaController {

    private final AperturaService aperturaService;
    @GetMapping("/{idJugador}")
    @Operation(summary = "Listar aperturas del jugador", description = "Lista todas las aperturas realizadas por el jugador indicando cartas obtenidas, fecha, etc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se mostro la lista"),
            @ApiResponse(responseCode = "400", description = "Error al mostrar lista")
    })
    public ResponseEntity<List<AperturaDTO>> listar(@Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        return ResponseEntity.ok(aperturaService.listarPorJugador(idJugador));
    }

    @PostMapping("/{idJugador}")
    @Operation(summary = "Abrir un sobre", description = "Permite a un jugador abrir un sobre y recibir cartas que este contenga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sobre abierto correctamente"),
            @ApiResponse(responseCode = "400", description = "El jugador no tiene el sobre a abrir")
    })
    public ResponseEntity<AperturaDTO> abrir(@Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador, @Valid @RequestBody AbrirSobreDTO dto) {
        AperturaDTO resultado = aperturaService.abrir(idJugador, dto);
        if (resultado == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
