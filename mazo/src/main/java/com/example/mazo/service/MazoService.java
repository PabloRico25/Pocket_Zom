package com.example.mazo.service;

import com.example.mazo.cliente.PerfilCliente;
import com.example.mazo.model.Mazo;
import com.example.mazo.repository.MazoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MazoService {

    private final MazoRepository mazoRepository;
    private final PerfilCliente perfilClient;
    public List<Mazo> listarPorJugador(Long idJugador) {
        return mazoRepository.findByIdJugador(idJugador);
    }
    public Mazo buscarPorId(Long id) {
        return mazoRepository.findById(id).orElse(null);
    }
    public Mazo crear(Long idJugador, Mazo mazo) {
        if (!Boolean.TRUE.equals(perfilClient.existeJugador(idJugador))) {

            log.warn("Jugador {} no existe", idJugador);
            return null;
        }
        if (Boolean.TRUE.equals(mazo.getEsActivo())) {
            mazoRepository.findByIdJugadorAndEsActivoTrue(idJugador).ifPresent(m -> {
                m.setEsActivo(false);
                mazoRepository.save(m);
            });
        }
        mazo.setIdJugador(idJugador);
        if (mazo.getEsActivo() == null) mazo.setEsActivo(false);
        Mazo guardado = mazoRepository.save(mazo);

        log.info("Mazo creado: {} para jugador {}", guardado.getIdMazo(), idJugador);
        return guardado;
    }
    public Mazo actualizar(Long id, Mazo nuevo) {
        Mazo existente = mazoRepository.findById(id).orElse(null);
        if (existente == null) {

            log.warn("Mazo no encontrado: {}", id);
            return null;
        }
        if (Boolean.TRUE.equals(nuevo.getEsActivo()) && !Boolean.TRUE.equals(existente.getEsActivo())) {
            mazoRepository.findByIdJugadorAndEsActivoTrue(existente.getIdJugador()).ifPresent(m -> {
                m.setEsActivo(false);
                mazoRepository.save(m);
            });
        }
        if (nuevo.getNombre() != null) existente.setNombre(nuevo.getNombre());
        if (nuevo.getEsActivo() != null) existente.setEsActivo(nuevo.getEsActivo());
        Mazo actualizado = mazoRepository.save(existente);

        log.info("Mazo actualizado: {}", id);
        return actualizado;
    }
    public boolean eliminar(Long id) {
        if (!mazoRepository.existsById(id)) {

            log.warn("Mazo no encontrado para eliminar: {}", id);
            return false;
        }
        mazoRepository.deleteById(id);

        log.info("Mazo eliminado: {}", id);
        return true;
    }
}