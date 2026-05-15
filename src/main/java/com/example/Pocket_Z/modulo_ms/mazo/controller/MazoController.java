package com.example.Pocket_Z.modulo_ms.mazo.controller;

import com.example.Pocket_Z.modulo_ms.mazo.model.Mazo;
import com.example.Pocket_Z.modulo_ms.mazo.services.MazoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mazos")
@RequiredArgsConstructor
public class MazoController {
    private final MazoService mazoService;

    @GetMapping("/jugador/{jugadorId}")
    public List<Mazo> listarPorJugador(@PathVariable Long jugadorId) {
        return mazoService.listarPorJugador(jugadorId);
    }

    @GetMapping("/jugador/{jugadorId}/activo")
    public ResponseEntity<Mazo> obtenerActivo(@PathVariable Long jugadorId) {
        Mazo activo = mazoService.obtenerMazoActivo(jugadorId);
        return activo != null ? ResponseEntity.ok(activo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Mazo crear(@RequestBody Mazo mazo) {
        return mazoService.guardar(mazo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mazo> actualizar(@PathVariable Long id, @RequestBody Mazo mazo) {
        if (mazoService.listarPorJugador(mazo.getJugadorId()).stream().noneMatch(m -> m.getId().equals(id))) {
            return ResponseEntity.notFound().build();
        }
        mazo.setId(id);
        return ResponseEntity.ok(mazoService.guardar(mazo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mazoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}