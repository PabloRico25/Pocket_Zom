package com.example.perfil.controller;

import com.example.perfil.model.Faccion;
import com.example.perfil.service.FaccionService;
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
@RequestMapping("/api/v1/facciones")
@RequiredArgsConstructor
@Tag(name = "Facciones", description = "Operaciones relacionadas con las Facciones del sistema")
public class FaccionController {

    private final FaccionService faccionService;

    @GetMapping
    @Operation(summary = "Listar facciones", description = "Obtiene una lista de todas las facciones disponibles en el sistema")
    @ApiResponses(value ={
            @ApiResponse( responseCode = "200", description = "Facciones encontradas"),
            @ApiResponse( responseCode = "204", description = "No existen facciones registradas")
    })

    public ResponseEntity<List<Faccion>> listar() {
        List<Faccion> lista = faccionService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar facción por ID", description = "Obtiene una faccion por ID referencial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faccion encontrada"),
            @ApiResponse(responseCode = "404", description = "Faccion no encontrada")
    })
    public ResponseEntity<Faccion> buscarPorId(
            @Parameter(description = "ID de la faccion", required = true) @PathVariable Long id) {
        Faccion faccion = faccionService.buscarPorId(id);
        if (faccion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faccion);
    }

    @PostMapping
    @Operation(summary = "Crear faccion", description = "Crea una nueva faccion en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Faccion creada exitosamente"),
            @ApiResponse(responseCode = "409", description = "Esta faccion ya existe")
    })
    public ResponseEntity<Faccion> crear(@Valid @RequestBody Faccion faccion) {
        Faccion nueva = faccionService.crear(faccion);
        if (nueva == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar faccion", description = "Actualiza los datos de una faccion que ya existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faccion actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Faccion no encontrada, no se a podido actualizar")
    })
    public ResponseEntity<Faccion> actualizar(
            @Parameter(description = "ID de la facción", required = true) @PathVariable Long id,
            @Valid @RequestBody Faccion faccion) {
        Faccion actualizada = faccionService.actualizar(id, faccion);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar faccion", description = "Elimina una faccion del sistema atravez de ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Facción eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Facción no encontrada, no se apodido eliminar")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la faccion", required = true) @PathVariable Long id) {
        boolean eliminada = faccionService.eliminar(id);
        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}