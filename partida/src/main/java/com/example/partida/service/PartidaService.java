package com.example.partida.service;

import com.example.partida.client.BilleteraClient;
import com.example.partida.client.RangoClient;
import com.example.partida.dto.FinalizarPartidaDTO;
import com.example.partida.dto.PartidaDTO;
import com.example.partida.model.Partida;
import com.example.partida.repository.PartidaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class PartidaService {
    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private BilleteraClient billeteraClient;
    @Autowired
    private RangoClient rangoClient;
    public List<PartidaDTO> listarTodas() {
        List<Partida> partidas = partidaRepository.findAll();
        List<PartidaDTO> resultado = new ArrayList<>();
        for (Partida partida : partidas) {
            PartidaDTO dto = toDTO(partida);
            resultado.add(dto);
        }
        return resultado;
    }
    public List<PartidaDTO> listarPorJugador(Long jugadorId) {
        List<Partida> partidas = partidaRepository.findByJugador1IdOrJugador2Id(jugadorId, jugadorId);
        List<PartidaDTO> resultado = new ArrayList<>();
        for (Partida partida : partidas) {
            PartidaDTO dto = toDTO(partida);
            resultado.add(dto);
        }
        return resultado;
    }
    public PartidaDTO obtenerPorId(Long id) {
        Optional<Partida> optional = partidaRepository.findById(id);
        if (optional.isPresent()) {
            Partida p = optional.get();
            return toDTO(p);
        } else {
            return null;
        }
    }
    @Transactional
    public PartidaDTO crearPartida(PartidaDTO dto) {
        Partida p = new Partida();
        p.setJugador1Id(dto.getJugador1Id());
        p.setJugador2Id(dto.getJugador2Id());
        p.setMazoJ1Id(dto.getMazoJ1Id());
        p.setMazoJ2Id(dto.getMazoJ2Id());
        p.setEstado("EN_CURSO");
        p.setFechaInicio(LocalDateTime.now());
        p = partidaRepository.save(p);
        log.info("Partida creada: {} entre {} y {}", p.getId(), p.getJugador1Id(), p.getJugador2Id());
        return toDTO(p);
    }
    @Transactional
    public PartidaDTO finalizarPartida(Long id, FinalizarPartidaDTO dto) {
        Partida p = partidaRepository.findById(id).orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        if (!"EN_CURSO".equals(p.getEstado())) {
            throw new RuntimeException("La partida ya está finalizada");
        }
        p.setEstado("FINALIZADA");
        p.setGanadorId(dto.getGanadorId());
        p.setFechaFin(LocalDateTime.now());
        p = partidaRepository.save(p);

        try {
            billeteraClient.registrarMovimiento(dto.getGanadorId(), "INGRESO", 100, "Premio por ganar partida " + id);
        } catch (Exception e) {
            log.error("Error al premiar al ganador en billetera: {}", e.getMessage());
        }
        try {
            Long perdedorId = p.getJugador1Id().equals(dto.getGanadorId()) ? p.getJugador2Id() : p.getJugador1Id();
            rangoClient.actualizarRanking(dto.getGanadorId(), true, 10);
            rangoClient.actualizarRanking(perdedorId, false, -5);
        } catch (Exception e) {
            log.error("Error al actualizar ranking: {}", e.getMessage());
        }

        log.info("Partida {} finalizada. Ganador: {}", id, dto.getGanadorId());
        return toDTO(p);
    }
    public void eliminarPartida(Long id) {
        if (!partidaRepository.existsById(id)) throw new RuntimeException("Partida no encontrada");
        partidaRepository.deleteById(id);
    }
    private PartidaDTO toDTO(Partida p) {
        PartidaDTO dto = new PartidaDTO();
        dto.setId(p.getId());
        dto.setJugador1Id(p.getJugador1Id());
        dto.setJugador2Id(p.getJugador2Id());
        dto.setMazoJ1Id(p.getMazoJ1Id());
        dto.setMazoJ2Id(p.getMazoJ2Id());
        dto.setGanadorId(p.getGanadorId());
        dto.setEstado(p.getEstado());
        dto.setFechaInicio(p.getFechaInicio());
        dto.setFechaFin(p.getFechaFin());
        return dto;
    }
}
