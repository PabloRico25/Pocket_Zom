package com.example.perfil.service;

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
        var existente = jugadorFaccionRepository.findByJugadorIdAndFaccionId(jugadorId, faccionId);
        if (existente.isPresent()) {
            throw new RuntimeException("El jugador ya pertenece a esta facción");
        }
        var jugador = jugadorService.obtenerEntidad(jugadorId);
        var faccion = faccionService.obtenerEntidad(faccionId);
        var jf = new com.example.perfil.model.JugadorFaccion();
        jf.setJugador(jugador);
        jf.setFaccion(faccion);
        jugadorFaccionRepository.save(jf);
        log.info("Jugador {} se unió a la facción {}", jugadorId, faccionId);
    }

    @Transactional
    public void salir(Long jugadorId, Long faccionId) {
        var jf = jugadorFaccionRepository.findByJugadorIdAndFaccionId(jugadorId, faccionId)
                .orElseThrow(() -> new RuntimeException("El jugador no pertenece a esta facción"));
        jugadorFaccionRepository.delete(jf);
        log.info("Jugador {} salió de la facción {}", jugadorId, faccionId);
    }

    public List<Long> listarFaccionesDeJugador(Long jugadorId) {
        return jugadorFaccionRepository.findByJugadorId(jugadorId)
                .stream().map(jf -> jf.getFaccion().getId()).collect(Collectors.toList());
    }
}