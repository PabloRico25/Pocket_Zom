package com.example.perfil.controller;

import com.example.perfil.model.JugadorFaccion;
import com.example.perfil.service.JugadorFaccionService;
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
@RequestMapping("/api/v1/jugador-faccion")
@RequiredArgsConstructor
@Tag(name = "Jugador-Faccion", description = "Operaciones relacionadas con la pertenencia de jugadores a facciones")
public class JugadorFaccionController {

    private final JugadorFaccionService jugadorFaccionService;

    @GetMapping("/{idJugador}")
    @Operation(summary = "Listar facciones de un jugador", description = "Obtiene la lista de facciones a las que pertenece el jugador segun el ID ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facciones del jugador encontradas correctamente"),
            @ApiResponse(responseCode = "204", description = "El jugador no pertenece a ninguna facción")
    })
    public ResponseEntity<List<JugadorFaccion>> listarPorJugador(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        List<JugadorFaccion> lista = jugadorFaccionService.listarPorJugador(idJugador);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{idJugador}/{idFaccion}")
    @Operation(summary = "Unir jugador a facción", description = "Une un jugador a una facción. El jugador y la facción deben existir, y el jugador no debe pertenecer a ninguna otra facción previa (regla de negocio: un jugador solo puede pertenecer a una facción a la vez)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Jugador unido a la facción correctamente"),
            @ApiResponse(responseCode = "400", description = "El jugador o la facción no existen, o el jugador ya pertenece a otra facción")
    })
    public ResponseEntity<JugadorFaccion> unir(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador,
            @Parameter(description = "ID de la facción", required = true) @PathVariable Long idFaccion) {
        JugadorFaccion resultado = jugadorFaccionService.unir(idJugador, idFaccion);
        if (resultado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @DeleteMapping("/{idJugador}/{idFaccion}")
    @Operation(summary = "Salir de una facción", description = "Elimina la relación entre un jugador y una facción a la que pertenece")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "El jugador salió de la facción correctamente"),
            @ApiResponse(responseCode = "404", description = "El jugador no pertenece a la facción indicada")
    })
    public ResponseEntity<Void> salir(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador,
            @Parameter(description = "ID de la facción", required = true) @PathVariable Long idFaccion) {
        boolean salio = jugadorFaccionService.salir(idJugador, idFaccion);
        if (!salio) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}