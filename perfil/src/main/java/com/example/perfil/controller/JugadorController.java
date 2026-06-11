package com.example.perfil.controller;

import com.example.perfil.model.Jugador;
import com.example.perfil.service.JugadorService;
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
@RequestMapping("/api/v1/jugadores")
@RequiredArgsConstructor
@Tag(name = "Jugadores", description = "Operaciones relacionadas con los jugadores del sistema")
public class JugadorController {

    private final JugadorService jugadorService;

    @GetMapping
    @Operation(summary = "Listar todos los jugadores", description = "Obtiene una lista con todos los jugadores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jugadores encontrados correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen jugadores registrados")
    })
    public ResponseEntity<List<Jugador>> listarTodos() {
        List<Jugador> lista = jugadorService.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar jugador por ID", description = "Obtiene un jugador especifico segun su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jugador encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe un jugador, con el ID indicado")
    })
    public ResponseEntity<Jugador> buscarPorId(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long id) {
        Jugador jugador = jugadorService.buscarPorId(id);
        if (jugador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jugador);
    }

    @GetMapping("/{id}/existe")
    @Operation(summary = "Verificar existencia de jugador", description = "Verifica si un jugador existe en el sistema segun el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve true si el jugador existe, false en caso contrario")
    })
    public ResponseEntity<Boolean> existe(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.existeJugador(id));
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo jugador", description = "Registra un nuevo jugador en el sistema. El nombre de usuario y el email deben ser únicos. Se asigna automaticamente el rol ROLE_PLAYER y nivel inicial 1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Jugador registrado correctamente"),
            @ApiResponse(responseCode = "409", description = "Ya existe un jugador con ese nombre de usuario o email")
    })
    public ResponseEntity<Jugador> registrar(@Valid @RequestBody Jugador jugador) {
        Jugador nuevo = jugadorService.registrar(jugador);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales del jugador segun nombre de usuario y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credenciales validas, devuelve los datos del jugador"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<Jugador> login(@RequestBody Jugador jugador) {
        Jugador encontrado = jugadorService.login(jugador.getNombreUsuario(), jugador.getPassword());
        if (encontrado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(encontrado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar jugador", description = "Actualiza los datos de un jugador existente segun el ID ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jugador actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe un jugador con el ID indicado")
    })
    public ResponseEntity<Jugador> actualizar(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long id,
            @Valid @RequestBody Jugador jugador) {
        Jugador actualizado = jugadorService.actualizar(id, jugador);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar jugador", description = "Elimina un jugador existente segun el ID ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Jugador eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe un jugador con el ID indicado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long id) {
        boolean eliminado = jugadorService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}