package com.example.mazo.service;

import com.example.mazo.cliente.PerfilClient;
import com.example.mazo.dto.MazoDTO;
import com.example.mazo.model.Mazo;
import com.example.mazo.repository.MazoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MazoService {
    private final MazoRepository mazoRepository;
    private final PerfilClient perfilClient;

    public List<MazoDTO> listarPorJugador(Long jugadorId) {
        return mazoRepository.findByJugadorId(jugadorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MazoDTO obtenerPorId(Long id) {
        return mazoRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Transactional
    public MazoDTO crearMazo(Long jugadorId, MazoDTO dto) {
        if (!perfilClient.existeJugador(jugadorId)) {
            throw new RuntimeException("Jugador no existe");
        }
        if (Boolean.TRUE.equals(dto.getEsActivo())) {
            mazoRepository.findByJugadorIdAndEsActivoTrue(jugadorId)
                    .ifPresent(activo -> {
                        activo.setEsActivo(false);
                        mazoRepository.save(activo);
                        log.info("Mazo {} desactivado", activo.getId());
                    });
        }
        Mazo m = new Mazo();
        m.setJugadorId(jugadorId);
        m.setNombre(dto.getNombre());
        m.setEsActivo(dto.getEsActivo() != null ? dto.getEsActivo() : false);
        m.setFechaCreacion(LocalDateTime.now());
        m = mazoRepository.save(m);
        log.info("Mazo creado: {} para jugador {}", m.getId(), jugadorId);
        return toDTO(m);
    }
    @Transactional
    public MazoDTO actualizarMazo(Long id, MazoDTO dto) {
        Mazo m = mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mazo no encontrado"));
        // Si se activa y no lo estaba, desactivar otros del jugador
        if (Boolean.TRUE.equals(dto.getEsActivo()) && !Boolean.TRUE.equals(m.getEsActivo())) {
            mazoRepository.findByJugadorIdAndEsActivoTrue(m.getJugadorId())
                    .ifPresent(activo -> {
                        activo.setEsActivo(false);
                        mazoRepository.save(activo);
                    });
        }
        if (dto.getNombre() != null) m.setNombre(dto.getNombre());
        if (dto.getEsActivo() != null) m.setEsActivo(dto.getEsActivo());
        m = mazoRepository.save(m);
        log.info("Mazo {} actualizado", m.getId());
        return toDTO(m);
    }
    @Transactional
    public void eliminarMazo(Long id) {
        if (!mazoRepository.existsById(id)) {
            throw new RuntimeException("Mazo no encontrado");
        }
        mazoRepository.deleteById(id);
        log.info("Mazo {} eliminado", id);
    }
    public Mazo obtenerEntidad(Long id) {
        return mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mazo no encontrado"));
    }
    private MazoDTO toDTO(Mazo m) {
        MazoDTO dto = new MazoDTO();
        dto.setId(m.getId());
        dto.setJugadorId(m.getJugadorId());
        dto.setNombre(m.getNombre());
        dto.setEsActivo(m.getEsActivo());
        dto.setFechaCreacion(m.getFechaCreacion());
        return dto;
    }
}