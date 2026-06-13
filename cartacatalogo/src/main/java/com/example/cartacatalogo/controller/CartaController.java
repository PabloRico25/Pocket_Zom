package com.example.cartacatalogo.controller;

import com.example.cartacatalogo.model.Carta;
import com.example.cartacatalogo.service.CartaService;
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
@RequestMapping("/api/v1/cartas")
@RequiredArgsConstructor
@Tag(name = "Cartas", description = "Operaciones relacionadas con el catalogo de cartas del juego")
public class CartaController {
    private final CartaService cartaService;

    @GetMapping
    @Operation(summary = "Listar todas las cartas", description = "Obtener una listado de todas las cartas del catalogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartas encontradas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No existen cartas en el catalogo"),
    })
    public ResponseEntity<List<Carta>> listar() {
        List<Carta> cartas = cartaService.listar();
        if (cartas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cartas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar carta por ID", description = "Obtener una carta especifica atraves del ID ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carta encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carta no encontrada")
    })
    public ResponseEntity<Carta> obtenerPorId(
            @Parameter(description = "ID de la carta", required = true) @PathVariable Long id) {
        Carta carta = cartaService.obtenerPorId(id);
        return carta != null ? ResponseEntity.ok(carta) : ResponseEntity.notFound().build();
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar por codigo de carta", description = "Obtener carta atravez de su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carta encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carta no encontrada")
    })
    public ResponseEntity<Carta> obtenerPorCodigo(
            @Parameter(description = "Codigo de la carta (por ejemplo ZMB-001)", required = true) @PathVariable String codigo) {
        Carta carta = cartaService.obtenerPorCodigo(codigo);
        return carta != null ? ResponseEntity.ok(carta) : ResponseEntity.notFound().build();
    }

    @GetMapping("/codigo/{codigo}/existe")
    @Operation(summary = "Verificar existencia por codigo", description = "Verificar excistencia de una carta a traves de su codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve true si la carta existe, false en caso contrario"),
            @ApiResponse(responseCode = "404", description = "Carta no encontrada")
    })
    public ResponseEntity<Boolean> existePorCodigo(
            @Parameter(description = "Codigo de la carta (por ejemplo ZMB-001)", required = true) @PathVariable String codigo) {
        return ResponseEntity.ok(cartaService.existePorCodigo(codigo));
    }

    @PostMapping
    @Operation(summary = "Crear nueva carta", description = "Crear una nueva carta en el catalogo. El código se normaliza automaticamente a mayúsculas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carta creada exitosamente"),
            @ApiResponse(responseCode = "409", description = "Ya existe una carta con el codigo indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
    })
    public ResponseEntity<Carta> crear(@Valid @RequestBody Carta carta) {
        Carta creada = cartaService.crear(carta);
        if (creada == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar carta", description = "Actualizar carta atravez de su ID. El código se normaliza automaticamente a mayúsculas ideal para actualizar el estado de una carta (activa/inactiva) sin modificar su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carta actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carta no encontrada"),
            @ApiResponse(responseCode = "409", description = "Ya existe una carta con el codigo indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida")
    })
    public ResponseEntity<Carta> actualizar(@PathVariable Long id, @Valid @RequestBody Carta carta) {
        Carta actualizada = cartaService.actualizar(id, carta);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar carta", description = "Eliminar carta atravez de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carta no encontrada")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la carta", required = true) @PathVariable Long id){
        boolean eliminado = cartaService.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}