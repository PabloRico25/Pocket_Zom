package com.example.perfil.controller;

import com.example.perfil.model.Jugador;
import com.example.perfil.service.JugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/jugadores")
@RequiredArgsConstructor
public class JugadorController {

    private final JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<List<Jugador>> listarTodos() {
        List<Jugador> lista = jugadorService.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> buscarPorId(@PathVariable Long id) {
        Jugador jugador = jugadorService.buscarPorId(id);
        if (jugador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jugador);
    }
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existe(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.existeJugador(id));
    }
    @PostMapping("/registro")
    public ResponseEntity<Jugador> registrar(@Valid @RequestBody Jugador jugador) {
        Jugador nuevo = jugadorService.registrar(jugador);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PostMapping("/login")
    public ResponseEntity<Jugador> login(@RequestBody Jugador jugador) {
        Jugador encontrado = jugadorService.login(jugador.getNombreUsuario(), jugador.getPassword());
        if (encontrado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(encontrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizar(@PathVariable Long id, @Valid @RequestBody Jugador jugador) {
        Jugador actualizado = jugadorService.actualizar(id, jugador);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = jugadorService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}