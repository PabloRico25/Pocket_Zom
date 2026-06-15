package com.example.publicacion.controller;

import com.example.publicacion.dto.CompraDTO;
import com.example.publicacion.dto.TransaccionDTO;
import com.example.publicacion.service.TransaccionService;
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
@RequestMapping("/api/v1/transacciones")
@RequiredArgsConstructor
@Tag(name = "Transaccion", description = "Operaciones relacionadas transacciones dentro del juego")
public class TransaccionController {
    private final TransaccionService transaccionService;

    @PostMapping("/{compradorId}")
    @Operation(summary = "Compra", description = "Realizar una compra sobre una publicacion existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra completada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al comprar")
    })
    public ResponseEntity<TransaccionDTO> comprar(@Parameter(description = "ID del comprador", required = true) @PathVariable Long compradorId, @Valid @RequestBody CompraDTO dto) {
        TransaccionDTO resultado = transaccionService.comprar(compradorId, dto.getPublicacionId());
        if (resultado == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/comprador/{compradorId}")
    @Operation(summary = "Listar compras", description = "Listar compras por id del comprador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compras listadas correctamente")
    })
    public ResponseEntity<List<TransaccionDTO>> listarCompras(@Parameter(description = "ID del comprador", required = true) @PathVariable Long compradorId) {
        return ResponseEntity.ok(transaccionService.listarPorComprador(compradorId));
    }
}
