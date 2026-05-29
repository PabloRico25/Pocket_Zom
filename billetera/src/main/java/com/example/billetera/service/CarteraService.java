package com.example.billetera.service;

import com.example.billetera.model.Cartera;
import com.example.billetera.repository.CarteraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarteraService {

    private final CarteraRepository carteraRepository;

    public List<Cartera> listar() {
        return carteraRepository.findAll();
    }

    public Cartera buscarPorJugador(Long idJugador) {
        return carteraRepository.findByIdJugador(idJugador).orElse(null);
    }

    public Cartera crear(Long idJugador) {

        if (carteraRepository.findByIdJugador(idJugador).isPresent()) {
            log.warn("El jugador {} ya tiene una cartera", idJugador);
            return null;
        }
        Cartera cartera = new Cartera();
        cartera.setIdJugador(idJugador);
        cartera.setSaldo(0);
        cartera.setUltimaActualizacion(LocalDateTime.now());

        Cartera guardada = carteraRepository.save(cartera);
        log.info("Cartera creada para jugador {} (id={})", idJugador, guardada.getIdCartera());
        return guardada;
    }

    public Cartera guardar(Cartera cartera) {
        cartera.setUltimaActualizacion(LocalDateTime.now());
        return carteraRepository.save(cartera);
    }
}
