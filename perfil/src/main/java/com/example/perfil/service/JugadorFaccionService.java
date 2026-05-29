package com.example.perfil.service;

import com.example.perfil.model.JugadorFaccion;
import com.example.perfil.repository.FaccionRepository;
import com.example.perfil.repository.JugadorFaccionRepository;
import com.example.perfil.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JugadorFaccionService {

    private final JugadorFaccionRepository jugadorFaccionRepository;
    private final JugadorRepository jugadorRepository;
    private final FaccionRepository faccionRepository;

    public List<JugadorFaccion> listarPorJugador(Long idJugador) {
        return jugadorFaccionRepository.findByIdJugador(idJugador);
    }
    public JugadorFaccion unir(Long idJugador, Long idFaccion) {
        if (!jugadorRepository.existsById(idJugador)) {
            log.warn("Jugador no encontrado: {}", idJugador);
            return null;
        }
        if (!faccionRepository.existsById(idFaccion)) {
            log.warn("Facción no encontrada: {}", idFaccion);
            return null;
        }
        List<JugadorFaccion> faccionesActuales = jugadorFaccionRepository.findByIdJugador(idJugador);
        if (!faccionesActuales.isEmpty()) {
            log.warn("El jugador {} ya pertenece a una facción", idJugador);
            return null;
        }
        if (jugadorFaccionRepository.findByIdJugadorAndIdFaccion(idJugador, idFaccion).isPresent()) {
            log.warn("El jugador {} ya está en la facción {}", idJugador, idFaccion);
            return null;
        }
        JugadorFaccion jf = new JugadorFaccion();
        jf.setIdJugador(idJugador);
        jf.setIdFaccion(idFaccion);
        JugadorFaccion guardado = jugadorFaccionRepository.save(jf);
        log.info("Jugador {} unido a facción {}", idJugador, idFaccion);
        return guardado;
    }
    public boolean salir(Long idJugador, Long idFaccion) {
        JugadorFaccion jf = jugadorFaccionRepository.findByIdJugadorAndIdFaccion(idJugador, idFaccion).orElse(null);
        if (jf == null) {
            log.warn("El jugador {} no pertenece a la facción {}", idJugador, idFaccion);
            return false;
        }
        jugadorFaccionRepository.delete(jf);
        log.info("Jugador {} salió de facción {}", idJugador, idFaccion);
        return true;
    }
}