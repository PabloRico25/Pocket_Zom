package com.example.billetera.service;

import com.example.billetera.dto.CarteraResponseDTO;
import com.example.billetera.model.Cartera;
import com.example.billetera.repository.CarteraRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CarteraService {
    @Autowired
    private CarteraRepository carteraRepository;

    public Cartera crearCartera(Long jugadorId) {
        log.info("Creando cartera para jugadorId: {}", jugadorId);
        Cartera existe = carteraRepository.findByJugadorId(jugadorId);
        if(existe != null){
            log.info("Ya existe cartera para jugador: " + jugadorId);
            return null;
        }
         Cartera nuevo = new Cartera();
        nuevo.setJugadorId(jugadorId);
        nuevo.setSaldo(0);
        nuevo.setUltimaActualizacion(LocalDateTime.now());
        return carteraRepository.save(nuevo);
    }

    public Cartera obtenerPorJugador(Long jugadorId){
        return carteraRepository.findByJugadorId(jugadorId);
    }

    public Cartera guardar(Cartera cartera){
        if(cartera == null){
            return null;
        }else{
            cartera.setUltimaActualizacion(LocalDateTime.now());
            return carteraRepository.save(cartera);
        }
    }

    public CarteraResponseDTO toDTO(Cartera c) {
        return new CarteraResponseDTO(c.getId(), c.getJugadorId(), c.getSaldo());
    }
}