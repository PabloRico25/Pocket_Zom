package com.example.mazo.controller;

import com.example.mazo.dto.AgregarCartaMazoDTO;
import com.example.mazo.model.MazoCarta;
import com.example.mazo.service.MazoCartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mazos/{idMazo}/cartas")
@RequiredArgsConstructor
public class MazoCartaController {

    private final MazoCartaService mazoCartaService;

    @GetMapping
    public ResponseEntity<List<MazoCarta>> listar(@PathVariable Long idMazo) {
        List<MazoCarta> lista = mazoCartaService.listarCartas(idMazo);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<MazoCarta> agregar(@PathVariable Long idMazo, @Valid @RequestBody AgregarCartaMazoDTO dto) {
        MazoCarta result = mazoCartaService.agregar(idMazo, dto.getCodigoCarta(), dto.getCantidad());
        if (result == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{codigoCarta}")
    public ResponseEntity<Void> quitar(@PathVariable Long idMazo,@PathVariable String codigoCarta,@RequestParam Integer cantidad) {
        boolean ok = mazoCartaService.quitar(idMazo, codigoCarta, cantidad);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/limpiar")
    public ResponseEntity<Void> limpiar(@PathVariable Long idMazo) {
        mazoCartaService.limpiar(idMazo);
        return ResponseEntity.noContent().build();
    }
}
