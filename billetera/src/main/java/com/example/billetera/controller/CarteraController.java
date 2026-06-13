package com.example.billetera.controller;

import com.example.billetera.model.Cartera;
import com.example.billetera.service.CarteraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carteras")
@RequiredArgsConstructor
@Tag(name = "Carteras", description = "Operaciones relacionadas con las carteras de los jugadores")
public class CarteraController {

    private final CarteraService carteraService;

    @GetMapping
    @Operation(summary = "Listar todas las carteras", description = "Obtiene una lista con todas las carteras registradas en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carteras encontradas correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen carteras registradas")
    })
    public ResponseEntity<List<Cartera>> listar() {
        List<Cartera> lista = carteraService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idJugador}")
    @Operation(summary = "Buscar cartera por jugador", description = "Obtiene la cartera asociada al jugador segun el ID ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartera encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "El jugador no tiene cartera registrada")
    })
    public ResponseEntity<Cartera> buscarPorJugador(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        Cartera cartera = carteraService.buscarPorJugador(idJugador);
        if (cartera == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartera);
    }

    @PostMapping("/{idJugador}")
    @Operation(summary = "Crear cartera", description = "Crea una nueva cartera para el jugador indicado. Cada jugador solo puede tener una cartera. El saldo inicial es 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cartera creada correctamente"),
            @ApiResponse(responseCode = "409", description = "El jugador ya tiene una cartera registrada")
    })
    public ResponseEntity<Cartera> crear(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        Cartera nueva = carteraService.crear(idJugador);
        if (nueva == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
}