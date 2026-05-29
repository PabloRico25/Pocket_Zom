package com.example.mazo.service;

import com.example.mazo.cliente.InventarioCliente;
import com.example.mazo.model.Mazo;
import com.example.mazo.model.MazoCarta;
import com.example.mazo.repository.MazoCartaRepository;
import com.example.mazo.repository.MazoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MazoCartaService {

    private final MazoCartaRepository mazoCartaRepository;
    private final MazoRepository mazoRepository;
    private final InventarioCliente inventarioClient;
    public List<MazoCarta> listarCartas(Long idMazo) {
        return mazoCartaRepository.findByIdMazo(idMazo);
    }
    public MazoCarta agregar(Long idMazo, String codigoCarta, Integer cantidad) {
        Mazo mazo = mazoRepository.findById(idMazo).orElse(null);
        if (mazo == null) {
            log.warn("Mazo no encontrado: {}", idMazo);
            return null;
        }
        if (!Boolean.TRUE.equals(inventarioClient.tieneCarta(mazo.getIdJugador(), codigoCarta, cantidad))) {
            log.warn("Jugador {} no tiene {} copias de {}", mazo.getIdJugador(), cantidad, codigoCarta);
            return null;
        }
        String codigo = codigoCarta.trim().toUpperCase();
        MazoCarta existente = mazoCartaRepository.findByIdMazoAndCodigoCarta(idMazo, codigo).orElse(null);
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);

            log.info("Carta {} actualizada en mazo {}", codigo, idMazo);
            return mazoCartaRepository.save(existente);
        }
        MazoCarta nueva = new MazoCarta();
        nueva.setIdMazo(idMazo);
        nueva.setCodigoCarta(codigo);
        nueva.setCantidad(cantidad);

        log.info("Carta {} añadida a mazo {}", codigo, idMazo);
        return mazoCartaRepository.save(nueva);
    }
    public boolean quitar(Long idMazo, String codigoCarta, Integer cantidad) {
        String codigo = codigoCarta.trim().toUpperCase();
        MazoCarta carta = mazoCartaRepository.findByIdMazoAndCodigoCarta(idMazo, codigo).orElse(null);
        if (carta == null) return false;
        int nuevaCantidad = carta.getCantidad() - cantidad;
        if (nuevaCantidad <= 0) {
            mazoCartaRepository.delete(carta);
        } else {
            carta.setCantidad(nuevaCantidad);
            mazoCartaRepository.save(carta);
        }

        log.info("Carta {} quitada de mazo {}", codigo, idMazo);
        return true;
    }
    public void limpiar(Long idMazo) {
        mazoCartaRepository.deleteByIdMazo(idMazo);

        log.info("Mazo {} limpiado", idMazo);
    }
}