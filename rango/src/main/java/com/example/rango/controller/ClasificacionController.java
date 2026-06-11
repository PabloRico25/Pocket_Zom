package com.example.rango.controller;

import com.example.rango.dto.ClasificacionDTO;
import com.example.rango.service.ClasificacionService;
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
@RequestMapping("/api/v1/ranking")
@Tag(name = "Rangos", description = "Operaciones relacionadas con los Rangos")
public class ClasificacionController {
    @Autowired

    private ClasificacionService clasificacionService;

    @GetMapping("/top")
    @Operation(summary = "Obtener todos los rangos", description = "Obtiene una lista con todos los rangos creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "No existen rangos")
    })
    public ResponseEntity<List<ClasificacionDTO>> top() {
        return ResponseEntity.ok(clasificacionService.obtenerRanking());
    }

    @GetMapping("/{jugadorId}")
    @Operation(summary = "Obtener rango por jugador", description = "Obtiene el rango que tiene el jugador de la id ingresada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "No existen jugador o no tiene rango")
    })
    @Parameter(description = "Id del jugador", required = true)
    public ResponseEntity<ClasificacionDTO> obtenerPorJugador(@PathVariable Long jugadorId) {
        ClasificacionDTO dto = clasificacionService.obtenerPorJugador(jugadorId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{jugadorId}")
    @Operation(summary = "Asigna rango a jugador", description = "Asigna un rango  al jugador de la id ingresada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "No existen jugador")
    })
    @Parameter(description = "Id del jugador", required = true)
    public ResponseEntity<ClasificacionDTO> crear(@PathVariable Long jugadorId) {
        ClasificacionDTO nueva = clasificacionService.crearClasificacion(jugadorId);
        if (nueva == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        }
    }

    @PutMapping("/{jugadorId}")
    @Operation(summary = "Actualizar rango por jugador", description = "Actualiza el rango que tiene el jugador de la id ingresada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "No existen jugador")
    })
    @Parameter(description = "Id del jugador", required = true)
    @Parameter(description = "Victoria", required = true)
    public ResponseEntity<Void> actualizar(@PathVariable Long jugadorId,@RequestParam boolean esVictoria,@RequestParam(required = false) Integer cambioElo) {
        try {
            int cambio = cambioElo != null ? cambioElo : (esVictoria ? 10 : -5);
            clasificacionService.actualizarRanking(jugadorId, esVictoria, cambio);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
