package com.example.Pocket_Z.modulo_ms.inventario.services;

import com.example.Pocket_Z.modulo_ms.inventario.model.Inventario;
import com.example.Pocket_Z.modulo_ms.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository inventarioRepository;

    public Inventario crearInventario(Long jugadorId) {
        Inventario inventario = new Inventario();
        inventario.setJugadorId(jugadorId);
        return inventarioRepository.save(inventario);
    }

    public Optional<Inventario> obtenerPorJugador(Long jugadorId) {
        return inventarioRepository.findByJugadorId(jugadorId);
    }

    public void eliminar(Long id) {
        inventarioRepository.deleteById(id);
    }
}