package com.example.Pocket_Z.modulo_ms.perfil.controller;

import com.example.Pocket_Z.modulo_ms.perfil.model.*;
import com.example.Pocket_Z.modulo_ms.perfil.services.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    // Jugadores
    @GetMapping("/jugadores")
    public List<Jugador> listarJugadores() {
        return perfilService.listarJugadores();
    }

    @GetMapping("/jugadores/{id}")
    public ResponseEntity<Jugador> obtenerJugador(@PathVariable Long id) {
        Jugador j = perfilService.obtenerJugador(id);
        return j != null ? ResponseEntity.ok(j) : ResponseEntity.notFound().build();
    }

    @PostMapping("/jugadores")
    @ResponseStatus(HttpStatus.CREATED)
    public Jugador crearJugador(@RequestBody Jugador jugador,
                                @RequestParam String rolNombre) {
        return perfilService.crearJugador(jugador, rolNombre);
    }

    @DeleteMapping("/jugadores/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable Long id) {
        perfilService.eliminarJugador(id);
        return ResponseEntity.noContent().build();
    }

    // Roles (solo para inicializar)
    @PostMapping("/roles")
    public Rol crearRol(@RequestParam String nombre) {
        return perfilService.crearRol(nombre);
    }

    // Facciones
    @GetMapping("/facciones")
    public List<Faccion> listarFacciones() {
        return perfilService.listarFacciones();
    }

    @PostMapping("/facciones")
    public Faccion crearFaccion(@RequestParam String nombre,
                                @RequestParam Long liderId) {
        return perfilService.crearFaccion(nombre, liderId);
    }

    // Unirse a facción
    @PostMapping("/unirse")
    public JugadorFaccion unirAFaccion(@RequestParam Long jugadorId,
                                       @RequestParam Long faccionId) {
        return perfilService.unirAFaccion(jugadorId, faccionId);
    }

    @GetMapping("/jugador-facciones/{jugadorId}")
    public List<JugadorFaccion> faccionesDeJugador(@PathVariable Long jugadorId) {
        return perfilService.obtenerFaccionesDeJugador(jugadorId);
    }
}