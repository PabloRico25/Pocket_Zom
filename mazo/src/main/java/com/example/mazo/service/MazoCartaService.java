package com.example.mazo.service;

import com.example.mazo.dto.MazoCartaRequestDTO;
import com.example.mazo.dto.MazoCartaResponseDTO;
import com.example.mazo.model.Mazo;
import com.example.mazo.model.MazoCarta;
import com.example.mazo.repository.MazoCartaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MazoCartaService {
    private final MazoCartaRepository mazoCartaRepository;
    private final MazoService mazoService;

    public List<MazoCartaResponseDTO> listarCartasDeMazo(Long mazoId) {
        return mazoCartaRepository.findByMazoId(mazoId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MazoCartaResponseDTO agregarCarta(Long mazoId, MazoCartaRequestDTO dto) {
        Mazo mazo = mazoService.obtenerMazoEntity(mazoId);
        String codigoNormalizado = dto.getCodigoCarta().trim().toUpperCase();

        // Buscar si la carta ya está en el mazo
        MazoCarta existente = mazoCartaRepository.findByMazoIdAndCodigoCarta(mazoId, codigoNormalizado)
                .orElse(null);

        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + dto.getCantidad());
            MazoCarta actualizada = mazoCartaRepository.save(existente);
            log.info("Cantidad actualizada para carta {} en mazo {}", codigoNormalizado, mazoId);
            return convertirADTO(actualizada);
        } else {
            MazoCarta nueva = new MazoCarta();
            nueva.setMazo(mazo);
            nueva.setCodigoCarta(codigoNormalizado);
            nueva.setCantidad(dto.getCantidad());
            MazoCarta guardada = mazoCartaRepository.save(nueva);
            log.info("Carta {} añadida al mazo {}", codigoNormalizado, mazoId);
            return convertirADTO(guardada);
        }
    }

    @Transactional
    public void quitarCarta(Long mazoId, String codigoCarta, Integer cantidad) {
        String codigoNormalizado = codigoCarta.trim().toUpperCase();
        MazoCarta carta = mazoCartaRepository.findByMazoIdAndCodigoCarta(mazoId, codigoNormalizado)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada en el mazo"));
        int nuevaCantidad = carta.getCantidad() - cantidad;
        if (nuevaCantidad <= 0) {
            mazoCartaRepository.delete(carta);
            log.info("Carta {} eliminada del mazo {}", codigoNormalizado, mazoId);
        } else {
            carta.setCantidad(nuevaCantidad);
            mazoCartaRepository.save(carta);
            log.info("Cantidad de {} reducida a {} en mazo {}", codigoNormalizado, nuevaCantidad, mazoId);
        }
    }

    @Transactional
    public void limpiarMazo(Long mazoId) {
        mazoCartaRepository.deleteByMazoId(mazoId);
        log.info("Mazo {} limpiado (todas las cartas eliminadas)", mazoId);
    }

    private MazoCartaResponseDTO convertirADTO(MazoCarta mc) {
        return new MazoCartaResponseDTO(mc.getId(), mc.getCodigoCarta(), mc.getCantidad());
    }
}