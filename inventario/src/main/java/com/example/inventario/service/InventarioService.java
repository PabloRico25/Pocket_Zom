package com.example.inventario.service;

import com.example.inventario.dto.InventarioResponseDTO;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository inventarioRepository;

    public Inventario crearInventario(Long jugadorId) {
        if (inventarioRepository.findByJugadorId(jugadorId).isPresent()) {
            throw new RuntimeException("El jugador " + jugadorId + " ya tiene un inventario");
        }
        Inventario inv = new Inventario();
        inv.setJugadorId(jugadorId);
        inv.setFechaCreacion(LocalDateTime.now());
        return inventarioRepository.save(inv);
    }

    public Optional<Inventario> obtenerPorJugador(Long jugadorId) {
        return inventarioRepository.findByJugadorId(jugadorId);
    }

    public InventarioResponseDTO toDTO(Inventario inv) {
        return new InventarioResponseDTO(inv.getId(), inv.getJugadorId(), inv.getFechaCreacion());
    }
}