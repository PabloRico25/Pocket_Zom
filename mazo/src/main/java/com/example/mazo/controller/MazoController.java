package com.example.mazo.controller;

import com.example.mazo.model.Mazo;
import com.example.mazo.service.MazoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mazos")
@RequiredArgsConstructor
public class MazoController {

    private final MazoService mazoService;
    @GetMapping("/jugador/{idJugador}")
    public ResponseEntity<List<Mazo>> listarPorJugador(@PathVariable Long idJugador) {
        List<Mazo> lista = mazoService.listarPorJugador(idJugador);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Mazo> buscar(@PathVariable Long id) {
        Mazo mazo = mazoService.buscarPorId(id);
        if (mazo == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mazo);
    }
    @PostMapping("/{idJugador}")
    public ResponseEntity<Mazo> crear(@PathVariable Long idJugador, @Valid @RequestBody Mazo mazo) {
        Mazo nuevo = mazoService.crear(idJugador, mazo);
        if (nuevo == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Mazo> actualizar(@PathVariable Long id, @Valid @RequestBody Mazo mazo) {
        Mazo actualizado = mazoService.actualizar(id, mazo);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean ok = mazoService.eliminar(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
