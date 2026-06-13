package com.example.mazo.controller;

import com.example.mazo.dto.MazoDTO;
import com.example.mazo.model.Mazo;
import com.example.mazo.service.MazoService;
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
@RequestMapping("/api/v1/mazos")
@RequiredArgsConstructor
@Tag(name = "Mazos", description = "Operaciones relacionadas con los mazos de los jugadores")
public class MazoController {

    private final MazoService mazoService;

    @GetMapping("/jugador/{idJugador}")
    @Operation(summary = "Listar mazos del jugador", description = "Obtiene la lista de mazos creados por el jugador segun el ID ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mazos encontrados correctamente"),
            @ApiResponse(responseCode = "204", description = "El jugador no tiene mazos creados")
    })
    public ResponseEntity<List<Mazo>> listarPorJugador(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        List<Mazo> lista = mazoService.listarPorJugador(idJugador);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mazo por ID", description = "Obtiene un mazo especifico segun el ID ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mazo encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe un mazo con el ID indicado")
    })
    public ResponseEntity<Mazo> buscar(
            @Parameter(description = "ID del mazo", required = true) @PathVariable Long id) {
        Mazo mazo = mazoService.buscarPorId(id);
        if (mazo == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mazo);
    }

    @PostMapping("/{idJugador}")
    @Operation(summary = "Crear nuevo mazo", description = "Crea un nuevo mazo para el jugador. El jugador debe existir en perfil (validado via Feign). Solo puede haber un mazo activo por jugador a la vez")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mazo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "El jugador no existe en el microservicio perfil")
    })
    public ResponseEntity<Mazo> crear(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador,
            @Valid @RequestBody MazoDTO dto) {
        Mazo mazo = new Mazo();
        mazo.setIdJugador(idJugador);
        mazo.setNombre(dto.getNombre());
        mazo.setEsActivo(dto.getEsActivo() != null ? dto.getEsActivo() : false);

        Mazo nuevo = mazoService.crear(idJugador, mazo);
        if (nuevo == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mazo", description = "Actualiza los datos de un mazo existente. Si se activa un mazo, los demas mazos activos del jugador pasan a inactivos (regla de negocio: solo un mazo activo por jugador)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mazo actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe un mazo con el ID indicado")
    })
    public ResponseEntity<Mazo> actualizar(
            @Parameter(description = "ID del mazo", required = true) @PathVariable Long id,
            @Valid @RequestBody MazoDTO dto) {
        Mazo mazo = new Mazo();
        mazo.setNombre(dto.getNombre());
        mazo.setEsActivo(dto.getEsActivo());

        Mazo actualizado = mazoService.actualizar(id, mazo);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mazo", description = "Elimina un mazo segun el ID ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mazo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe un mazo con el ID indicado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del mazo", required = true) @PathVariable Long id) {
        boolean ok = mazoService.eliminar(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

