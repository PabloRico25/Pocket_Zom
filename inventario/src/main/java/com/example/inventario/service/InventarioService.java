package com.example.inventario.service;

import com.example.inventario.dto.InventarioDTO;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository inventarioRepository;

    @Transactional
    public InventarioDTO crearInventario(Long jugadorId) {
        if (inventarioRepository.findByJugadorId(jugadorId).isPresent()) {
            throw new RuntimeException("El jugador " + jugadorId + " ya tiene un inventario");
        }
        Inventario inv = new Inventario();
        inv.setJugadorId(jugadorId);
        inv = inventarioRepository.save(inv);
        log.info("Inventario creado para jugador {} con id {}", jugadorId, inv.getId());
        return toDTO(inv);
    }

    public InventarioDTO obtenerPorJugador(Long jugadorId) {
        return inventarioRepository.findByJugadorId(jugadorId)
                .map(this::toDTO)
                .orElse(null);
    }

    // Método interno usado por CartaUsuarioService
    public Inventario obtenerEntidad(Long jugadorId) {
        return inventarioRepository.findByJugadorId(jugadorId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para jugador " + jugadorId));
    }

    private InventarioDTO toDTO(Inventario inv) {
        InventarioDTO dto = new InventarioDTO();
        dto.setId(inv.getId());
        dto.setJugadorId(inv.getJugadorId());
        dto.setFechaCreacion(inv.getFechaCreacion());
        return dto;
    }
}