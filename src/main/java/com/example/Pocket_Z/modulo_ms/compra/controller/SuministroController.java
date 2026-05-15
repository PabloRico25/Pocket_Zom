package com.example.Pocket_Z.modulo_ms.compra.controller;

import com.example.Pocket_Z.modulo_ms.compra.model.Suministro;
import com.example.Pocket_Z.modulo_ms.compra.services.SuministroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suministros")
@RequiredArgsConstructor
public class SuministroController {
    private final SuministroService suministroService;

    @GetMapping
    public List<Suministro> listar() {
        return suministroService.listar();
    }

    @PostMapping
    public Suministro crear(@RequestBody Suministro suministro) {
        return suministroService.guardar(suministro);
    }

    @PutMapping("/{id}")
    public Suministro actualizar(@PathVariable Long id, @RequestBody Suministro suministro) {
        suministro.setId(id);
        return suministroService.guardar(suministro);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        suministroService.eliminar(id);
    }
}