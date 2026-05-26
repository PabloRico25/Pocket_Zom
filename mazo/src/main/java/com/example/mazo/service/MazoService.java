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
        return mazoRepository.findByJugadorId(jugadorId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MazoDTO obtenerPorId(Long id) {
        Mazo mazo = mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mazo no encontrado con id " + id));
        return toDTO(mazo);
    }

    @Transactional
    public MazoDTO crearMazo(Long jugadorId, MazoDTO dto) {
        // Validar que el jugador existe
        if (!perfilClient.existeJugador(jugadorId)) {
            throw new RuntimeException("El jugador " + jugadorId + " no existe");
        }
        // Si el nuevo mazo es activo, desactivar cualquier otro mazo activo del mismo jugador
        if (dto.getEsActivo() != null && dto.getEsActivo()) {
            mazoRepository.findByJugadorIdAndEsActivoTrue(jugadorId)
                    .ifPresent(activo -> {
                        activo.setEsActivo(false);
                        mazoRepository.save(activo);
                        log.info("Mazo activo {} desactivado", activo.getId());
                    });
        }
        Mazo mazo = new Mazo();
        mazo.setJugadorId(jugadorId);
        mazo.setNombre(dto.getNombre());
        mazo.setEsActivo(dto.getEsActivo() != null ? dto.getEsActivo() : false);
        mazo = mazoRepository.save(mazo);
        log.info("Mazo creado con id {} para jugador {}", mazo.getId(), jugadorId);
        return toDTO(mazo);
    }

    @Transactional
    public MazoDTO actualizarMazo(Long id, MazoDTO dto) {
        Mazo mazo = mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mazo no encontrado con id " + id));
        // Si se marca como activo y no lo era, desactivar otros mazos del mismo jugador
        if (dto.getEsActivo() != null && dto.getEsActivo() && !Boolean.TRUE.equals(mazo.getEsActivo())) {
            mazoRepository.findByJugadorIdAndEsActivoTrue(mazo.getJugadorId())
                    .ifPresent(activo -> {
                        activo.setEsActivo(false);
                        mazoRepository.save(activo);
                        log.info("Mazo activo {} desactivado", activo.getId());
                    });
        }
        mazo.setNombre(dto.getNombre());
        if (dto.getEsActivo() != null) {
            mazo.setEsActivo(dto.getEsActivo());
        }
        mazo = mazoRepository.save(mazo);
        log.info("Mazo {} actualizado", mazo.getId());
        return toDTO(mazo);
    }

    @Transactional
    public void eliminarMazo(Long id) {
        if (!mazoRepository.existsById(id)) {
            throw new RuntimeException("Mazo no encontrado con id " + id);
        }
        mazoRepository.deleteById(id);
        log.info("Mazo {} eliminado", id);
    }

    // Método interno para obtener entidad (usado por MazoCartaService)
    public Mazo obtenerEntidad(Long id) {
        return mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mazo no encontrado con id " + id));
    }

    private MazoDTO toDTO(Mazo mazo) {
        MazoDTO dto = new MazoDTO();
        dto.setId(mazo.getId());
        dto.setJugadorId(mazo.getJugadorId());
        dto.setNombre(mazo.getNombre());
        dto.setEsActivo(mazo.getEsActivo());
        dto.setFechaCreacion(mazo.getFechaCreacion());
        return dto;
    }


}