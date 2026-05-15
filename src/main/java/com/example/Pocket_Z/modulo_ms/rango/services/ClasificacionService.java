package com.example.Pocket_Z.modulo_ms.rango.services;

import com.example.Pocket_Z.modulo_ms.rango.model.Clasificacion;
import com.example.Pocket_Z.modulo_ms.rango.repository.ClasificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClasificacionService {
    private final ClasificacionRepository clasificacionRepository;

    public Clasificacion crearClasificacion(Long jugadorId) {
        Clasificacion clasificacion = new Clasificacion();
        clasificacion.setJugadorId(jugadorId);
        clasificacion.setPuntosElo(1000);
        clasificacion.setVictorias(0);
        clasificacion.setDerrotas(0);
        clasificacion.setRangoActual("Bronce");
        return clasificacionRepository.save(clasificacion);
    }

    public Optional<Clasificacion> obtenerPorJugador(Long jugadorId) {
        return clasificacionRepository.findByJugadorId(jugadorId);
    }

    public Clasificacion actualizarEstadisticas(Long jugadorId, boolean gano, int cambioElo) {
        Clasificacion clasificacion = clasificacionRepository.findByJugadorId(jugadorId)
                .orElseThrow(() -> new RuntimeException("Clasificación no encontrada"));

        if (gano) {
            clasificacion.setVictorias(clasificacion.getVictorias() + 1);
        } else {
            clasificacion.setDerrotas(clasificacion.getDerrotas() + 1);
        }
        clasificacion.setPuntosElo(clasificacion.getPuntosElo() + cambioElo);

        // Actualizar rango según puntos ELO
        int elo = clasificacion.getPuntosElo();
        if (elo < 1200) clasificacion.setRangoActual("Bronce");
        else if (elo < 1500) clasificacion.setRangoActual("Plata");
        else if (elo < 1800) clasificacion.setRangoActual("Oro");
        else if (elo < 2100) clasificacion.setRangoActual("Platino");
        else clasificacion.setRangoActual("Legendario");

        return clasificacionRepository.save(clasificacion);
    }

    public List<Clasificacion> obtenerRanking() {
        return clasificacionRepository.findAllByOrderByPuntosEloDesc();
    }

    public void eliminar(Long id) {
        clasificacionRepository.deleteById(id);
    }
}