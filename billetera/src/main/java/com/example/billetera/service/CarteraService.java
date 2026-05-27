package com.example.billetera.service;

import com.example.billetera.dto.CarteraDTO;
import com.example.billetera.model.Cartera;
import com.example.billetera.repository.CarteraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarteraService {
    private final CarteraRepository carteraRepository;

    @Transactional
    public CarteraDTO crearCartera(Long jugadorId) {
        if (carteraRepository.findByJugadorId(jugadorId).isPresent()) {
            throw new RuntimeException("La cartera para el jugador " + jugadorId + " ya existe");
        }
        Cartera c = new Cartera();
        c.setJugadorId(jugadorId);
        c.setSaldo(0);
        c.setUltimaActualizacion(LocalDateTime.now());
        c = carteraRepository.save(c);
        log.info("Cartera creada para jugador {} con id {}", jugadorId, c.getId());
        return toDTO(c);
    }

    public CarteraDTO obtenerPorJugador(Long jugadorId) {
        return carteraRepository.findByJugadorId(jugadorId)
                .map(this::toDTO)
                .orElse(null);
    }

    public Cartera obtenerEntidad(Long jugadorId) {
        return carteraRepository.findByJugadorId(jugadorId)
                .orElseThrow(() -> new RuntimeException("Cartera no encontrada"));
    }

    public Cartera guardar(Cartera cartera) {
        cartera.setUltimaActualizacion(LocalDateTime.now());
        return carteraRepository.save(cartera);
    }

    private CarteraDTO toDTO(Cartera c) {
        CarteraDTO dto = new CarteraDTO();
        dto.setId(c.getId());
        dto.setJugadorId(c.getJugadorId());
        dto.setSaldo(c.getSaldo());
        return dto;
    }
}