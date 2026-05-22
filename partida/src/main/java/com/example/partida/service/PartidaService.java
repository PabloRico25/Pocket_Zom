package com.example.partida.service;

import com.example.partida.dto.FinalizarPartidaRequestDTO;
import com.example.partida.dto.PartidaRequestDTO;
import com.example.partida.dto.PartidaResponseDTO;
import com.example.partida.model.Partida;
import com.example.partida.repository.PartidaRepository;
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
public class PartidaService {
    private final PartidaRepository partidaRepository;

    public List<PartidaResponseDTO> listarTodas() {
        return partidaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<PartidaResponseDTO> listarPorJugador(Long jugadorId) {
        return partidaRepository.findByJugador1IdOrJugador2Id(jugadorId, jugadorId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PartidaResponseDTO obtenerPorId(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada con id " + id));
        return convertirADTO(partida);
    }

    @Transactional
    public PartidaResponseDTO crearPartida(Long jugador1Id, PartidaRequestDTO dto) {
        Partida partida = new Partida();
        partida.setJugador1Id(jugador1Id);
        partida.setJugador2Id(dto.getJugador2Id());
        partida.setMazoJ1Id(dto.getMazoJ1Id());
        partida.setMazoJ2Id(dto.getMazoJ2Id());
        partida.setEstado("EN_CURSO");
        partida.setFechaInicio(LocalDateTime.now());
        Partida guardada = partidaRepository.save(partida);
        log.info("Partida {} creada entre {} y {}", guardada.getId(), jugador1Id, dto.getJugador2Id());
        return convertirADTO(guardada);
    }

    @Transactional
    public PartidaResponseDTO finalizarPartida(Long id, FinalizarPartidaRequestDTO dto) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada con id " + id));
        if (!"EN_CURSO".equals(partida.getEstado())) {
            throw new RuntimeException("La partida ya está finalizada");
        }
        partida.setEstado("FINALIZADA");
        partida.setGanadorId(dto.getGanadorId());
        partida.setFechaFin(LocalDateTime.now());
        Partida actualizada = partidaRepository.save(partida);
        log.info("Partida {} finalizada. Ganador: {}", actualizada.getId(), dto.getGanadorId());
        return convertirADTO(actualizada);
    }

    @Transactional
    public void eliminarPartida(Long id) {
        if (!partidaRepository.existsById(id)) {
            throw new RuntimeException("Partida no encontrada con id " + id);
        }
        partidaRepository.deleteById(id);
        log.info("Partida {} eliminada", id);
    }

    private PartidaResponseDTO convertirADTO(Partida p) {
        return new PartidaResponseDTO(
                p.getId(),
                p.getJugador1Id(),
                p.getJugador2Id(),
                p.getMazoJ1Id(),
                p.getMazoJ2Id(),
                p.getGanadorId(),
                p.getEstado(),
                p.getFechaInicio(),
                p.getFechaFin()
        );
    }
}