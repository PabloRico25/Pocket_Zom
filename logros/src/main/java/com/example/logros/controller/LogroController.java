package com.example.logros.controller;

import com.example.logros.dto.LogroDTO;
import com.example.logros.service.LogroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logros")
@Tag(name = "Logro", description = "Operaciones relacionadas los logros de los jugadores")
public class LogroController {
    @Autowired
    private LogroService logroService;

    @GetMapping
    @Operation(summary = "Listar logros", description = "Obtiene la lista de los logros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de logros generados correctamente")
    })
    public ResponseEntity<List<LogroDTO>> listar() {
        return ResponseEntity.ok(logroService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener logro", description = "Obtiene la informacion sobre un logro especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logro encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Logro no encontrado")
    })
    public ResponseEntity<LogroDTO> obtener(@Parameter(description = "ID del logro", required = true) @PathVariable String id) {
        try {
            return ResponseEntity.ok(logroService.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar logros", description = "Listar logros por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado generado correctamente")
    })
    public ResponseEntity<List<LogroDTO>> listarPorTipo(@Parameter(description = "Tipo de logro", required = true) @PathVariable String tipo) {
        return ResponseEntity.ok(logroService.listarPorTipo(tipo));
    }

    @PostMapping
    @Operation(summary = "Crear un logro", description = "Se crea un logro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Logro creado"),
            @ApiResponse(responseCode = "409", description = "Error al crear")
    })
    public ResponseEntity<LogroDTO> crear(@Valid @RequestBody LogroDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logroService.crear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un logro", description = "Se actualiza la informacion de un logro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Logro no encontrado")
    })
    public ResponseEntity<LogroDTO> actualizar(@Parameter(description = "ID del logro", required = true) @PathVariable String id, @Valid @RequestBody LogroDTO dto) {
        try {
            return ResponseEntity.ok(logroService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un logro", description = "Elimina un lorgo mediante su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Se elimino el logro"),
            @ApiResponse(responseCode = "404", description = "No se encontro el logro a eliminar")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del logro", required = true) @PathVariable String id) {
        try {
            logroService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
