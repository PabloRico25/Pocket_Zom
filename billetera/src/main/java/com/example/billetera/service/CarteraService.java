package com.example.billetera.service;

import com.example.billetera.dto.CarteraResponseDTO;
import com.example.billetera.model.Cartera;
import com.example.billetera.repository.CarteraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarteraService {
    private final CarteraRepository carteraRepository;

    public Cartera crearCartera(Long jugadorId) {
        log.info("Creando cartera para jugadorId: {}", jugadorId);
        if (carteraRepository.findByJugadorId(jugadorId).isPresent()) {
            throw new RuntimeException("La cartera para el jugador " + jugadorId + " ya existe");
        }
        Cartera c = new Cartera();
        c.setJugadorId(jugadorId);
        c.setSaldo(0);
        c.setUltimaActualizacion(LocalDateTime.now());
        return carteraRepository.save(c);
    }

    public Optional<Cartera> obtenerPorJugador(Long jugadorId) {
        return carteraRepository.findByJugadorId(jugadorId);
    }

    public Cartera guardar(Cartera cartera) {
        cartera.setUltimaActualizacion(LocalDateTime.now());
        return carteraRepository.save(cartera);
    }

    public CarteraResponseDTO toDTO(Cartera c) {
        return new CarteraResponseDTO(c.getId(), c.getJugadorId(), c.getSaldo());
    }
}