package com.example.publicacion.controller;

import com.example.publicacion.dto.PublicacionDTO;
import com.example.publicacion.service.PublicacionService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/publicaciones")
@RequiredArgsConstructor
@Tag(name = "Publicacion", description = "Operaciones relacionadas a las publicaciones")
public class PublicacionController {
    private final PublicacionService publicacionService;

    @GetMapping("/activas")
    @Operation(summary = "Listar publicaciones activas", description = "Lista unicamente las publicaciones activas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicaciones activas listadas correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen publicaciones activas")
    })
    public ResponseEntity<List<PublicacionDTO>> listarActivas() {
        List<PublicacionDTO> lista = publicacionService.listarActivas();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/vendedor/{idVendedor}")
    @Operation(summary = "Listar por vendedor", description = "Listar publicaciones por vendedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicaciones listada correctamente"),
            @ApiResponse(responseCode = "404", description = "Error al listar publicaciones")
    })
    public ResponseEntity<List<PublicacionDTO>> listarPorVendedor(@PathVariable Long idVendedor) {
        return ResponseEntity.ok(publicacionService.listarPorVendedor(idVendedor));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar publicacion", description = "Obtiene informacion sobre la publicacion segun su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicacion encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Publicacion no encontrada o no existe")
    })
    public ResponseEntity<PublicacionDTO> buscar(@PathVariable Long id) {
        PublicacionDTO dto = publicacionService.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Crear publicacion", description = "Crear una publicacion sobre una carta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publicacion creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear una publicacion")
    })
    public ResponseEntity<PublicacionDTO> crear(@Valid @RequestBody PublicacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionService.crear(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar una publicacion", description = "Elimina una publicacion existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Borrado completado"),
            @ApiResponse(responseCode = "400", description = "Error al eliminar una publicacion")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        publicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
