package com.example.Pocket_Z.modulo_ms.partida.services;

import com.example.Pocket_Z.modulo_ms.partida.model.Partida;
import com.example.Pocket_Z.modulo_ms.partida.repository.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartidaService {
    private final PartidaRepository partidaRepository;

    public List<Partida> listar() {
        return partidaRepository.findAll();
    }

    public List<Partida> listarPorJugador(Long jugadorId) {
        return partidaRepository.findByJugador1IdOrJugador2Id(jugadorId, jugadorId);
    }

    public Optional<Partida> obtener(Long id) {
        return partidaRepository.findById(id);
    }

    public Partida crear(Partida partida) {
        partida.setId(null);
        partida.setEstado("EN_CURSO");
        partida.setFechaInicio(LocalDateTime.now());
        partida.setFechaFin(null);
        partida.setGanadorId(null);
        return partidaRepository.save(partida);
    }

    public Partida finalizarPartida(Long id, Long ganadorId) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        partida.setEstado("FINALIZADA");
        partida.setGanadorId(ganadorId);
        partida.setFechaFin(LocalDateTime.now());
        return partidaRepository.save(partida);
    }

    public void eliminar(Long id) {
        partidaRepository.deleteById(id);
    }
}