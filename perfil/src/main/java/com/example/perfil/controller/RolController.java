package com.example.perfil.controller;

import com.example.perfil.model.Rol;
import com.example.perfil.service.RolService;
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
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Operaciones relacionadas con los Roles del sistema")
public class RolController {

    private final RolService rolService;

    @GetMapping
    @Operation(summary = "Listar roles", description = "Obtiene una lista de todos los roles disponibles en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles encontrados exitosamente"),
            @ApiResponse(responseCode = "204", description = "No existen roles registrados")
    })
    public ResponseEntity<List<Rol>> listar() {
        List<Rol> lista = rolService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene un rol específico utilizando su ID como referencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles encontrados exitosamente"),
            @ApiResponse(responseCode = "204", description = "No existen roles registrados con el ID proporcionado")
    })
    public ResponseEntity<Rol> buscarPorId(
            @Parameter(description = "ID del rol", required = true) @PathVariable Long id) {
        Rol rol = rolService.buscarPorId(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rol);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo rol", description = "Registra un nuevo rol en el sistema.(El nombre del rol debe ser unico)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "Este rol ya se encuentra en uso")
    })
    public ResponseEntity<Rol> crear(@Valid @RequestBody Rol rol) {
        Rol nuevo = rolService.crear(rol);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}