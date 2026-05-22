package com.example.mazo.service;

import com.example.mazo.dto.MazoRequestDTO;
import com.example.mazo.dto.MazoResponseDTO;
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

    public List<MazoResponseDTO> listarPorJugador(Long jugadorId) {
        return mazoRepository.findByJugadorId(jugadorId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MazoResponseDTO obtenerPorId(Long id) {
        Mazo mazo = mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mazo no encontrado con id " + id));
        return convertirADTO(mazo);
    }

    public Mazo obtenerMazoEntity(Long id) {
        return mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mazo no encontrado con id " + id));
    }

    @Transactional
    public MazoResponseDTO crearMazo(Long jugadorId, MazoRequestDTO dto) {
        // Si el nuevo mazo es activo, desactivamos cualquier otro mazo activo del jugador
        if (dto.getEsActivo() != null && dto.getEsActivo()) {
            mazoRepository.findByJugadorIdAndEsActivoTrue(jugadorId)
                    .ifPresent(activo -> {
                        activo.setEsActivo(false);
                        mazoRepository.save(activo);
                        log.info("Mazo {} desactivado", activo.getId());
                    });
        }

        Mazo mazo = new Mazo();
        mazo.setJugadorId(jugadorId);
        mazo.setNombre(dto.getNombre());
        mazo.setEsActivo(dto.getEsActivo() != null ? dto.getEsActivo() : false);
        mazo.setFechaCreacion(LocalDateTime.now());
        Mazo guardado = mazoRepository.save(mazo);
        log.info("Mazo creado con id: {} para jugador {}", guardado.getId(), jugadorId);
        return convertirADTO(guardado);
    }

    @Transactional
    public MazoResponseDTO actualizarMazo(Long id, MazoRequestDTO dto) {
        Mazo mazo = mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mazo no encontrado con id " + id));

        // Si se marca como activo, desactivar otros mazos del mismo jugador
        if (dto.getEsActivo() != null && dto.getEsActivo() && !Boolean.TRUE.equals(mazo.getEsActivo())) {
            mazoRepository.findByJugadorIdAndEsActivoTrue(mazo.getJugadorId())
                    .ifPresent(activo -> {
                        activo.setEsActivo(false);
                        mazoRepository.save(activo);
                        log.info("Mazo {} desactivado", activo.getId());
                    });
        }

        mazo.setNombre(dto.getNombre());
        if (dto.getEsActivo() != null) {
            mazo.setEsActivo(dto.getEsActivo());
        }
        Mazo actualizado = mazoRepository.save(mazo);
        log.info("Mazo {} actualizado", actualizado.getId());
        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminarMazo(Long id) {
        if (!mazoRepository.existsById(id)) {
            throw new RuntimeException("Mazo no encontrado con id " + id);
        }
        mazoRepository.deleteById(id);
        log.info("Mazo {} eliminado", id);
    }

    private MazoResponseDTO convertirADTO(Mazo m) {
        return new MazoResponseDTO(m.getId(), m.getJugadorId(), m.getNombre(), m.getFechaCreacion(), m.getEsActivo());
    }
}