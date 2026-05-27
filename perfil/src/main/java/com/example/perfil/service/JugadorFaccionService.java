package com.example.perfil.service;

import com.example.perfil.model.JugadorFaccion;
import com.example.perfil.repository.JugadorFaccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JugadorFaccionService {
    private final JugadorFaccionRepository jugadorFaccionRepository;
    private final JugadorService jugadorService;
    private final FaccionService faccionService;

    @Transactional
    public void unir(Long jugadorId, Long faccionId) {
        if (!jugadorService.existeJugador(jugadorId)) {
            throw new RuntimeException("El jugador " + jugadorId + " no existe");
        }
        faccionService.obtenerPorId(faccionId);

        if (jugadorFaccionRepository.findByJugadorIdAndFaccionId(jugadorId, faccionId).isPresent()) {
            throw new RuntimeException("El jugador ya pertenece a esta facción");
        }

        JugadorFaccion jf = new JugadorFaccion();
        jf.setJugadorId(jugadorId);
        jf.setFaccionId(faccionId);
        jugadorFaccionRepository.save(jf);
        log.info("Jugador {} unido a facción {}", jugadorId, faccionId);
    }

    @Transactional
    public void salir(Long jugadorId, Long faccionId) {
        JugadorFaccion jf = jugadorFaccionRepository.findByJugadorIdAndFaccionId(jugadorId, faccionId)
                .orElseThrow(() -> new RuntimeException("El jugador no pertenece a esa facción"));
        jugadorFaccionRepository.delete(jf);
        log.info("Jugador {} salió de facción {}", jugadorId, faccionId);
    }

    public List<Long> listarFaccionesDeJugador(Long jugadorId) {
        return jugadorFaccionRepository.findByJugadorId(jugadorId).stream()
                .map(JugadorFaccion::getFaccionId)
                .collect(Collectors.toList());
    }
}