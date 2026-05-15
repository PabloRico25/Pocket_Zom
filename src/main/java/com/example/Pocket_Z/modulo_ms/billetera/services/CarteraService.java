package com.example.Pocket_Z.modulo_ms.billetera.services;

import com.example.Pocket_Z.modulo_ms.billetera.model.Cartera;
import com.example.Pocket_Z.modulo_ms.billetera.repository.CarteraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarteraService {
    private final CarteraRepository carteraRepository;

    public Cartera crearCartera(Long jugadorId) {
        Cartera cartera = new Cartera();
        cartera.setJugadorId(jugadorId);
        cartera.setSaldo(0);
        return carteraRepository.save(cartera);
    }

    public Optional<Cartera> obtenerPorJugador(Long jugadorId) {
        return carteraRepository.findByJugadorId(jugadorId);
    }

    public Cartera guardar(Cartera cartera) {
        cartera.setUltimaActualizacion(LocalDateTime.now());
        return carteraRepository.save(cartera);
    }

    public void eliminar(Long id) {
        carteraRepository.deleteById(id);
    }
}