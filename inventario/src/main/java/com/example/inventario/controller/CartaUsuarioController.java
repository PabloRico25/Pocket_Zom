package com.example.inventario.controller;

import com.example.inventario.dto.AgregarCartaDTO;
import com.example.inventario.dto.TransferirCartaDTO;
import com.example.inventario.model.CartaUsuario;
import com.example.inventario.service.CartaUsuarioService;
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
@RequestMapping("/api/v1/inventario/cartas")
@RequiredArgsConstructor
@Tag(name = "Cartas de Usuario", description = "Operaciones relacionadas con las cartas que posee cada jugador en su inventario")
public class CartaUsuarioController {

    private final CartaUsuarioService cartaUsuarioService;

    @GetMapping("/{idJugador}")
    @Operation(summary = "Listar cartas del jugador", description = "Obtiene la lista de cartas que posee el jugador en su inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartas encontradas correctamente"),
            @ApiResponse(responseCode = "204", description = "El jugador no tiene cartas en su inventario"),
            @ApiResponse(responseCode = "404", description = "El jugador no tiene inventario registrado")
    })
    public ResponseEntity<List<CartaUsuario>> listar(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador) {
        List<CartaUsuario> lista = cartaUsuarioService.listarCartas(idJugador);
        if (lista == null) return ResponseEntity.notFound().build();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/tiene")
    @Operation(summary = "Verificar si el jugador tiene una carta", description = "Verifica si el jugador posee la cantidad indicada de una carta especifica. Si no se indica cantidad, se verifica que tenga al menos 1 unidad de la carta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve true si el jugador tiene la cantidad indicada de la carta, false en caso contrario")
    })
    public ResponseEntity<Boolean> tieneCarta(
            @Parameter(description = "ID del jugador", required = true) @RequestParam Long idJugador,
            @Parameter(description = "Codigo de la carta", required = true) @RequestParam String codigoCarta,
            @Parameter(description = "Cantidad requerida (por defecto 1)", required = false) @RequestParam(required = false) Integer cantidad) {
        return ResponseEntity.ok(cartaUsuarioService.tieneCarta(idJugador, codigoCarta, cantidad));
    }

    @PostMapping("/{idJugador}/agregar")
    @Operation(summary = "Agregar carta al inventario", description = "Agrega una carta al inventario del jugador. El jugador debe existir en perfil y la carta debe existir en cartacatalogo . Si la carta ya existe en el inventario, suma la cantidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carta agregada correctamente al inventario"),
            @ApiResponse(responseCode = "400", description = "El jugador no existe, la carta no existe en el catalogo o el jugador no tiene inventario")
    })
    public ResponseEntity<CartaUsuario> agregar(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador,
            @Valid @RequestBody AgregarCartaDTO dto) {
        CartaUsuario resultado = cartaUsuarioService.agregar(idJugador, dto.getCodigoCarta(), dto.getCantidad());
        if (resultado == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @DeleteMapping("/{idJugador}")
    @Operation(summary = "Quitar carta del inventario", description = "Resta la cantidad indicada de una carta del inventario del jugador. Si la cantidad resultante es 0 o menor, la carta se elimina completamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carta quitada correctamente"),
            @ApiResponse(responseCode = "404", description = "El jugador no tiene inventario o no posee la carta indicada")
    })
    public ResponseEntity<Void> quitar(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Long idJugador,
            @Parameter(description = "Codigo de la carta a quitar", required = true) @RequestParam String codigoCarta,
            @Parameter(description = "Cantidad a quitar", required = true) @RequestParam Integer cantidad) {
        boolean ok = cartaUsuarioService.quitar(idJugador, codigoCarta, cantidad);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transferir")
    @Operation(summary = "Transferir carta entre jugadores", description = "Transfiere una carta del inventario de un jugador a otro. Se quita del jugador origen y se agrega al jugador destino. Usado por publicacion al concretar una venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carta transferida correctamente"),
            @ApiResponse(responseCode = "400", description = "El jugador origen no tiene la carta, o el destino no tiene inventario")
    })
    public ResponseEntity<Void> transferir(@Valid @RequestBody TransferirCartaDTO dto) {
        boolean ok = cartaUsuarioService.transferir(
                dto.getIdJugadorOrigen(),
                dto.getIdJugadorDestino(),
                dto.getCodigoCarta(),
                dto.getCantidad()
        );
        if (!ok) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }
}