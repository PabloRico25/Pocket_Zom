package com.example.cartacatalogo.service;

import com.example.cartacatalogo.dto.CartaDTO;
import com.example.cartacatalogo.model.Carta;
import com.example.cartacatalogo.repository.CartaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaService {
    private final CartaRepository cartaRepository;

    public List<CartaDTO> listar() {
        return cartaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CartaDTO obtenerPorId(Long id) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada" + id));
        return toDTO(carta);
    }

    public CartaDTO obtenerPorCodigo(String codigo) {
        String codigoNormalizado = codigo.trim().toUpperCase();
        Carta carta = cartaRepository.findByCodigo(codigoNormalizado)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada" + codigo));
        return toDTO(carta);
    }

    @Transactional
    public CartaDTO crear(CartaDTO dto) {
        String codigoNormalizado = dto.getCodigo().trim().toUpperCase();
        if (cartaRepository.existsByCodigo(codigoNormalizado)) {
            throw new RuntimeException(codigoNormalizado + " ya existe");
        }
        Carta carta = new Carta();
        carta.setCodigo(codigoNormalizado);
        carta.setNombre(dto.getNombre());
        carta.setRaza(dto.getRaza());
        carta.setAtaque(dto.getAtaque() != null ? dto.getAtaque() : 0);
        carta.setDefensa(dto.getDefensa() != null ? dto.getDefensa() : 0);
        carta.setCoste(dto.getCoste() != null ? dto.getCoste() : 0);
        carta.setHabilidad(dto.getHabilidad());
        carta.setActiva(dto.getActiva() != null ? dto.getActiva() : true);
        carta = cartaRepository.save(carta);
        log.info("Carta creada {}", carta.getId());
        return toDTO(carta);
    }

    @Transactional
    public CartaDTO actualizar(Long id, CartaDTO dto) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada" + id));
        String codigoNormalizado = dto.getCodigo().trim().toUpperCase();
        if (!carta.getCodigo().equals(codigoNormalizado) && cartaRepository.existsByCodigo(codigoNormalizado)) {
            throw new RuntimeException(codigoNormalizado + " ya existe");
        }
        carta.setCodigo(codigoNormalizado);
        carta.setNombre(dto.getNombre());
        carta.setRaza(dto.getRaza());
        carta.setAtaque(dto.getAtaque());
        carta.setDefensa(dto.getDefensa());
        carta.setCoste(dto.getCoste());
        carta.setHabilidad(dto.getHabilidad());
        if (dto.getActiva() != null) {
            carta.setActiva(dto.getActiva());
        }
        carta = cartaRepository.save(carta);
        log.info("Carta actualizada {}", carta.getId());
        return toDTO(carta);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!cartaRepository.existsById(id)) {
            throw new RuntimeException("Carta no encontrada" + id);
        }
        cartaRepository.deleteById(id);
        log.info("Carta eliminada{}", id);
    }

    private CartaDTO toDTO(Carta carta) {
        CartaDTO dto = new CartaDTO();
        dto.setId(carta.getId());
        dto.setCodigo(carta.getCodigo());
        dto.setNombre(carta.getNombre());
        dto.setRaza(carta.getRaza());
        dto.setAtaque(carta.getAtaque());
        dto.setDefensa(carta.getDefensa());
        dto.setCoste(carta.getCoste());
        dto.setHabilidad(carta.getHabilidad());
        dto.setActiva(carta.getActiva());
        return dto;
    }
}