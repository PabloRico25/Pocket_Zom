package com.example.rango.service;

import com.example.rango.dto.ClasificacionDTO;
import com.example.rango.model.Clasificacion;
import com.example.rango.repository.ClasificacionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ClasificacionService {
    @Autowired
    private ClasificacionRepository clasificacionRepository;

    public ClasificacionDTO crearClasificacion(Long jugadorId) {
        Optional<Clasificacion> existente = clasificacionRepository.findByJugadorId(jugadorId);
        if (existente.isPresent()) {
            return null;
        }
        Clasificacion nueva = new Clasificacion();
        nueva.setJugadorId(jugadorId);
        Clasificacion guardada = clasificacionRepository.save(nueva);
        return toDTO(guardada);
    }

    public ClasificacionDTO obtenerPorJugador(Long jugadorId) {
        Optional<Clasificacion> optional = clasificacionRepository.findByJugadorId(jugadorId);
        if (optional.isPresent()) {
            Clasificacion clasificacion = optional.get();
            return toDTO(clasificacion);
        } else {
            return null;
        }
    }

    public List<ClasificacionDTO> obtenerRanking() {
        List<Clasificacion> jugadores = clasificacionRepository.findAllByOrderByPuntosEloDesc();
        List<ClasificacionDTO> resultado = new ArrayList<>();
        for (Clasificacion jugador : jugadores) {
            ClasificacionDTO dto = toDTO(jugador);
            resultado.add(dto);
        }
        return resultado;
    }

    public void actualizarRanking(Long jugadorId, boolean esVictoria, int cambioElo) {
        Optional<Clasificacion> optional = clasificacionRepository.findByJugadorId(jugadorId);
        if (!optional.isPresent()) {
            log.info("No se encontró clasificación para el jugador: " + jugadorId);
            return;
        }
        Clasificacion c = optional.get();
        int nuevoElo = c.getPuntosElo() + cambioElo;
        if (nuevoElo < 0) {
            nuevoElo = 0;
        }
        c.setPuntosElo(nuevoElo);
        if (esVictoria) {
            c.setVictorias(c.getVictorias() + 1);
        } else {
            c.setDerrotas(c.getDerrotas() + 1);
        }
        if (nuevoElo < 1200) {
            c.setRangoActual("Bronce");
        } else if (nuevoElo < 1500) {
            c.setRangoActual("Plata");
        } else if (nuevoElo < 1800) {
            c.setRangoActual("Oro");
        } else if (nuevoElo < 2100) {
            c.setRangoActual("Platino");
        } else {
            c.setRangoActual("Legendario");
        }
        clasificacionRepository.save(c);
        log.info("Ranking actualizado para jugador " + jugadorId + ": nuevo ELO=" + c.getPuntosElo() +
                ", victorias=" + c.getVictorias() + ", derrotas=" + c.getDerrotas() +
                ", rango=" + c.getRangoActual());
    }

    private ClasificacionDTO toDTO(Clasificacion c) {
        ClasificacionDTO dto = new ClasificacionDTO();
        dto.setId(c.getId());
        dto.setJugadorId(c.getJugadorId());
        dto.setPuntosElo(c.getPuntosElo());
        dto.setVictorias(c.getVictorias());
        dto.setDerrotas(c.getDerrotas());
        dto.setRangoActual(c.getRangoActual());
        return dto;
    }
}
