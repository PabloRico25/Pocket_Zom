package com.example.inventario.service;

import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository inventarioRepository;
    public Inventario buscarPorJugador(Long idJugador) {
        return inventarioRepository.findByIdJugador(idJugador).orElse(null);
    }

    public Inventario crear(Long idJugador) {
        if (inventarioRepository.findByIdJugador(idJugador).isPresent()) {
            log.warn("El jugador {} ya tiene inventario", idJugador);
            return null;
        }

        Inventario inv = new Inventario();
        inv.setIdJugador(idJugador);
        Inventario guardado = inventarioRepository.save(inv);
        log.info("Inventario creado para jugador {}", idJugador);
        return guardado;
    }
}