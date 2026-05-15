package com.example.Pocket_Z.modulo_ms.mazo.controller;

import com.example.Pocket_Z.modulo_ms.mazo.model.Mazo;
import com.example.Pocket_Z.modulo_ms.mazo.services.MazoCartaService;
import com.example.Pocket_Z.modulo_ms.mazo.services.MazoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mazos/{mazoId}/cartas")
@RequiredArgsConstructor
public class MazoCartaController {
    private final MazoCartaService mazoCartaService;
    private final MazoService mazoService;

    @PostMapping
    public void agregar(@PathVariable Long mazoId,
                        @RequestParam String codigoCarta,
                        @RequestParam Integer cantidad) {
        Mazo mazo = mazoService.listarPorJugador(null).stream()
                .filter(m -> m.getId().equals(mazoId)).findFirst().orElseThrow();
        mazoCartaService.agregarCarta(mazo, codigoCarta, cantidad);
    }

    @DeleteMapping
    public void quitar(@PathVariable Long mazoId,
                       @RequestParam String codigoCarta,
                       @RequestParam Integer cantidad) {
        Mazo mazo = mazoService.listarPorJugador(null).stream()
                .filter(m -> m.getId().equals(mazoId)).findFirst().orElseThrow();
        mazoCartaService.quitarCarta(mazo, codigoCarta, cantidad);
    }
}