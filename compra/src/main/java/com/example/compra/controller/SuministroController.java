package com.example.compra.controller;

import com.example.compra.dto.SuministroDTO;
import com.example.compra.service.SuministroService;
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
@RequestMapping("/api/v1/suministros")
@RequiredArgsConstructor
@Tag(name = "Suministro", description = "Operaciones relacionadas a los sobre de cartas")
public class SuministroController {

    private final SuministroService suministroService;
    @GetMapping
    @Operation(summary = "Listar sobres de cartas", description = "Obtiene la lista de los sobres de cartas disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los sobres"),
            @ApiResponse(responseCode = "204", description = "No existen sobres a listar")
    })
    public ResponseEntity<List<SuministroDTO>> listar() {
        List<SuministroDTO> lista = suministroService.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar sobre de cartas", description = "Obtiene los datos sobre un sobre en especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se muestra la iformacion del sobre"),
            @ApiResponse(responseCode = "404", description = "No se encontro el sobre")
    })
    public ResponseEntity<SuministroDTO> obtener(@Parameter(description = "Id del sobre", required = true) @PathVariable Long id) {
        SuministroDTO dto = suministroService.obtener(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Crear sobre de cartas", description = "Crea un sobre de cartas usando la informacion entregada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sobre creado correctamente")
    })
    public ResponseEntity<SuministroDTO> crear(@Valid @RequestBody SuministroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(suministroService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un sobre de cartas", description = "Actualiza la ifnromacion y atributos de un sobre en especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sobre actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontro ningun sobre con esa id")
    })
    public ResponseEntity<SuministroDTO> actualizar(@Parameter(description = "Id del sobre", required = true) @PathVariable Long id, @Valid @RequestBody SuministroDTO dto) {
        SuministroDTO actualizado = suministroService.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un sobre de cartas", description = "Elimina un sobre de cartas en especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "El sobre fue eliminado")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "Id del sobre", required = true) @PathVariable Long id) {
        suministroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
