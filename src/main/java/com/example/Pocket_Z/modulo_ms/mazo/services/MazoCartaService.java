package com.example.Pocket_Z.modulo_ms.mazo.services;

import com.example.Pocket_Z.modulo_ms.mazo.model.Mazo;
import com.example.Pocket_Z.modulo_ms.mazo.model.MazoCarta;
import com.example.Pocket_Z.modulo_ms.mazo.repository.MazoCartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MazoCartaService {
    private final MazoCartaRepository mazoCartaRepository;

    public List<MazoCarta> listarCartasDeMazo(Long mazoId) {
        return mazoCartaRepository.findByMazoId(mazoId);
    }

    public void agregarCarta(Mazo mazo, String codigoCarta, Integer cantidad) {
        // Buscar si ya existe esa carta en el mazo
        List<MazoCarta> existentes = mazoCartaRepository.findByMazoId(mazo.getId());
        MazoCarta existente = existentes.stream()
                .filter(c -> c.getCodigoCarta().equals(codigoCarta))
                .findFirst().orElse(null);

        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);
            mazoCartaRepository.save(existente);
        } else {
            MazoCarta nueva = new MazoCarta();
            nueva.setMazo(mazo);
            nueva.setCodigoCarta(codigoCarta);
            nueva.setCantidad(cantidad);
            mazoCartaRepository.save(nueva);
        }
    }

    public void quitarCarta(Mazo mazo, String codigoCarta, Integer cantidad) {
        List<MazoCarta> existentes = mazoCartaRepository.findByMazoId(mazo.getId());
        MazoCarta existente = existentes.stream()
                .filter(c -> c.getCodigoCarta().equals(codigoCarta))
                .findFirst().orElse(null);
        if (existente != null) {
            int nuevaCantidad = existente.getCantidad() - cantidad;
            if (nuevaCantidad <= 0) {
                mazoCartaRepository.delete(existente);
            } else {
                existente.setCantidad(nuevaCantidad);
                mazoCartaRepository.save(existente);
            }
        }
    }

    public void limpiarMazo(Long mazoId) {
        mazoCartaRepository.deleteByMazoId(mazoId);
    }
}