package com.example.mazo.service;

import com.example.mazo.cliente.InventarioClient;
import com.example.mazo.dto.MazoCartaDTO;
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
    private final InventarioClient inventarioClient;

    @Transactional
    public MazoCartaDTO agregarCarta(Long mazoId, MazoCartaDTO dto) {
        Mazo mazo = mazoService.obtenerEntidad(mazoId);
        // Validar que el jugador posea suficientes copias en inventario
        Boolean tiene = inventarioClient.tieneCarta(mazo.getJugadorId(), dto.getCodigoCarta(), dto.getCantidad());
        if (!Boolean.TRUE.equals(tiene)) {
            throw new RuntimeException("El jugador no posee suficientes copias de la carta " + dto.getCodigoCarta());
        }
        String codigo = dto.getCodigoCarta().trim().toUpperCase();
        MazoCarta existente = mazoCartaRepository.findByMazoIdAndCodigoCarta(mazoId, codigo).orElse(null);
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + dto.getCantidad());
            existente = mazoCartaRepository.save(existente);
            log.info("Cantidad actualizada para carta {} en mazo {}", codigo, mazoId);
            return toDTO(existente);
        } else {
            MazoCarta nueva = new MazoCarta();
            nueva.setMazoId(mazoId);
            nueva.setCodigoCarta(codigo);
            nueva.setCantidad(dto.getCantidad());
            nueva = mazoCartaRepository.save(nueva);
            log.info("Carta {} añadida al mazo {}", codigo, mazoId);
            return toDTO(nueva);
        }
    }

    @Transactional
    public void quitarCarta(Long mazoId, String codigoCarta, Integer cantidad) {
        String codigo = codigoCarta.trim().toUpperCase();
        MazoCarta carta = mazoCartaRepository.findByMazoIdAndCodigoCarta(mazoId, codigo)
                .orElseThrow(() -> new RuntimeException("La carta no está en el mazo"));
        int nuevaCantidad = carta.getCantidad() - cantidad;
        if (nuevaCantidad <= 0) {
            mazoCartaRepository.delete(carta);
            log.info("Carta {} eliminada del mazo {}", codigo, mazoId);
        } else {
            carta.setCantidad(nuevaCantidad);
            mazoCartaRepository.save(carta);
            log.info("Cantidad de {} reducida a {} en mazo {}", codigo, nuevaCantidad, mazoId);
        }
    }

    public List<MazoCartaDTO> listarCartas(Long mazoId) {
        return mazoCartaRepository.findByMazoId(mazoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void limpiarMazo(Long mazoId) {
        mazoCartaRepository.deleteByMazoId(mazoId);
        log.info("Mazo {} limpiado", mazoId);
    }

    private MazoCartaDTO toDTO(MazoCarta mc) {
        MazoCartaDTO dto = new MazoCartaDTO();
        dto.setId(mc.getId());
        dto.setCodigoCarta(mc.getCodigoCarta());
        dto.setCantidad(mc.getCantidad());
        return dto;
    }
}