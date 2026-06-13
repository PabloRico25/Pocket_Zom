package com.example.mazo.controller;

import com.example.mazo.dto.AgregarCartaMazoDTO;
import com.example.mazo.model.MazoCarta;
import com.example.mazo.service.MazoCartaService;
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
@RequestMapping("/api/v1/mazos/{idMazo}/cartas")
@RequiredArgsConstructor
@Tag(name = "Cartas de Mazo", description = "Operaciones relacionadas con las cartas que conforman cada mazo")
public class MazoCartaController {

    private final MazoCartaService mazoCartaService;

    @GetMapping
    @Operation(summary = "Listar cartas del mazo", description = "Obtiene la lista de cartas que conforman el mazo indicado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartas encontradas correctamente"),
            @ApiResponse(responseCode = "204", description = "El mazo no tiene cartas")
    })
    public ResponseEntity<List<MazoCarta>> listar(
            @Parameter(description = "ID del mazo", required = true) @PathVariable Long idMazo) {
        List<MazoCarta> lista = mazoCartaService.listarCartas(idMazo);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Agregar carta al mazo", description = "Agrega una carta al mazo (El jugador debe tener la carta en su inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carta agregada con exito"),
            @ApiResponse(responseCode = "400", description = "El mazo no existe o el jugador no tiene la cantidad indicada de la carta en su inventario")
    })
    public ResponseEntity<MazoCarta> agregar(
            @Parameter(description = "ID del mazo", required = true) @PathVariable Long idMazo,
            @Valid @RequestBody AgregarCartaMazoDTO dto) {
        MazoCarta result = mazoCartaService.agregar(idMazo, dto.getCodigoCarta(), dto.getCantidad());
        if (result == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{codigoCarta}")
    @Operation(summary = "Quitar carta del mazo", description = "Resta la cantidad indicada de una carta del mazo. Si la cantidad resultante es 0 o menor, la carta se elimina completamente del mazo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carta quitada del mazo correctamente"),
            @ApiResponse(responseCode = "404", description = "El mazo no contiene la carta indicada")
    })
    public ResponseEntity<Void> quitar(
            @Parameter(description = "ID del mazo", required = true) @PathVariable Long idMazo,
            @Parameter(description = "Codigo de la carta a quitar", required = true) @PathVariable String codigoCarta,
            @Parameter(description = "Cantidad a quitar", required = true) @RequestParam Integer cantidad) {
        boolean ok = mazoCartaService.quitar(idMazo, codigoCarta, cantidad);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/limpiar")
    @Operation(summary = "Limpiar mazo", description = "Elimina todas las cartas del mazo, dejandolo vacio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mazo limpiado correctamente"),
            @ApiResponse(responseCode = "404", description = "El mazo no existe")
    })
    public ResponseEntity<Void> limpiar(
            @Parameter(description = "ID del mazo", required = true) @PathVariable Long idMazo) {
        mazoCartaService.limpiar(idMazo);
        return ResponseEntity.noContent().build();
    }
}