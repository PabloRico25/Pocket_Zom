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
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<PartidaResponseDTO> listarPorJugador(Long jugadorId) {
        return partidaRepository.findByJugador1IdOrJugador2Id(jugadorId, jugadorId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PartidaResponseDTO obtenerPorId(Long id) {
        Partida p = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        return toDTO(p);
    }

    @Transactional
    public PartidaResponseDTO crearPartida(Long jugador1Id, PartidaRequestDTO dto) {
        Partida p = new Partida();
        p.setJugador1Id(jugador1Id);
        p.setJugador2Id(dto.getJugador2Id());
        p.setMazoJ1Id(dto.getMazoJ1Id());
        p.setMazoJ2Id(dto.getMazoJ2Id());
        p.setEstado("EN_CURSO");
        p.setFechaInicio(LocalDateTime.now());
        Partida saved = partidaRepository.save(p);
        log.info("Partida {} creada entre {} y {}", saved.getId(), jugador1Id, dto.getJugador2Id());
        return toDTO(saved);
    }

    @Transactional
    public PartidaResponseDTO finalizarPartida(Long id, FinalizarPartidaRequestDTO dto) {
        Partida p = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        if (!"EN_CURSO".equals(p.getEstado())) {
            throw new RuntimeException("La partida ya está finalizada");
        }
        p.setEstado("FINALIZADA");
        p.setGanadorId(dto.getGanadorId());
        p.setFechaFin(LocalDateTime.now());
        Partida updated = partidaRepository.save(p);
        log.info("Partida {} finalizada. Ganador: {}", updated.getId(), dto.getGanadorId());
        return toDTO(updated);
    }

    @Transactional
    public void eliminarPartida(Long id) {
        if (!partidaRepository.existsById(id)) {
            throw new RuntimeException("Partida no encontrada");
        }
        partidaRepository.deleteById(id);
        log.info("Partida {} eliminada", id);
    }

    private PartidaResponseDTO toDTO(Partida p) {
        return new PartidaResponseDTO(
                p.getId(), p.getJugador1Id(), p.getJugador2Id(),
                p.getMazoJ1Id(), p.getMazoJ2Id(), p.getGanadorId(),
                p.getEstado(), p.getFechaInicio(), p.getFechaFin()
        );
    }
}