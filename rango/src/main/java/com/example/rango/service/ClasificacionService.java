package com.example.rango.service;

import com.example.rango.dto.ClasificacionRequestDTO;
import com.example.rango.dto.ClasificacionResponseDTO;
import com.example.rango.model.Clasificacion;
import com.example.rango.repository.ClasificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClasificacionService {
    private final ClasificacionRepository clasificacionRepository;

    public List<ClasificacionResponseDTO> obtenerRanking() {
        return clasificacionRepository.findAllByOrderByPuntosEloDesc()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ClasificacionResponseDTO obtenerPorJugador(Long jugadorId) {
        Clasificacion c = clasificacionRepository.findByJugadorId(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no tiene clasificación, créela primero"));
        return convertirADTO(c);
    }

    @Transactional
    public ClasificacionResponseDTO crearClasificacion(Long jugadorId) {
        if (clasificacionRepository.findByJugadorId(jugadorId).isPresent()) {
            throw new RuntimeException("La clasificación para el jugador " + jugadorId + " ya existe");
        }
        Clasificacion c = new Clasificacion();
        c.setJugadorId(jugadorId);
        c = clasificacionRepository.save(c);
        log.info("Clasificación creada para jugador {}", jugadorId);
        return convertirADTO(c);
    }

    @Transactional
    public ClasificacionResponseDTO actualizarEstadisticas(Long jugadorId, ClasificacionRequestDTO dto) {
        Clasificacion c = clasificacionRepository.findByJugadorId(jugadorId)
                .orElseThrow(() -> new RuntimeException("Clasificación no encontrada para el jugador " + jugadorId));

        int cambioElo = dto.getCambioElo() != null ? dto.getCambioElo() : (dto.getEsVictoria() ? 15 : -15);
        int nuevoElo = c.getPuntosElo() + cambioElo;
        if (nuevoElo < 0) nuevoElo = 0;
        c.setPuntosElo(nuevoElo);

        if (dto.getEsVictoria()) {
            c.setVictorias(c.getVictorias() + 1);
        } else {
            c.setDerrotas(c.getDerrotas() + 1);
        }

        // Actualizar rango basado en ELO
        if (nuevoElo < 1200) c.setRangoActual("Bronce");
        else if (nuevoElo < 1500) c.setRangoActual("Plata");
        else if (nuevoElo < 1800) c.setRangoActual("Oro");
        else if (nuevoElo < 2100) c.setRangoActual("Platino");
        else c.setRangoActual("Legendario");

        Clasificacion actualizada = clasificacionRepository.save(c);
        log.info("Estadísticas actualizadas para jugador {}: ELO={}, victorias={}, derrotas={}, rango={}",
                jugadorId, actualizada.getPuntosElo(), actualizada.getVictorias(),
                actualizada.getDerrotas(), actualizada.getRangoActual());
        return convertirADTO(actualizada);
    }

    @Transactional
    public void eliminarClasificacion(Long id) {
        if (!clasificacionRepository.existsById(id)) {
            throw new RuntimeException("Clasificación no encontrada");
        }
        clasificacionRepository.deleteById(id);
        log.info("Clasificación {} eliminada", id);
    }

    private ClasificacionResponseDTO convertirADTO(Clasificacion c) {
        return new ClasificacionResponseDTO(
                c.getId(),
                c.getJugadorId(),
                c.getPuntosElo(),
                c.getVictorias(),
                c.getDerrotas(),
                c.getRangoActual()
        );
    }
}