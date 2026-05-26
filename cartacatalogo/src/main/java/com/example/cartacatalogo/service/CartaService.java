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
        log.info("Listando todas las cartas");
        return cartaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CartaDTO obtenerPorId(Long id) {
        log.info("Buscando carta por id: {}", id);
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada con id " + id));
        return toDTO(carta);
    }

    public CartaDTO obtenerPorCodigo(String codigo) {
        log.info("Buscando carta por código: {}", codigo);
        String codigoNormalizado = codigo.trim().toUpperCase();
        Carta carta = cartaRepository.findByCodigo(codigoNormalizado)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada con código " + codigo));
        return toDTO(carta);
    }

    @Transactional
    public CartaDTO crear(CartaDTO dto) {
        log.info("Creando nueva carta: {}", dto);
        String codigoNormalizado = dto.getCodigo().trim().toUpperCase();
        if (cartaRepository.existsByCodigo(codigoNormalizado)) {
            throw new RuntimeException("El código " + codigoNormalizado + " ya existe");
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
        log.info("Carta creada con id: {}", carta.getId());
        return toDTO(carta);
    }

    @Transactional
    public CartaDTO actualizar(Long id, CartaDTO dto) {
        log.info("Actualizando carta con id {}: {}", id, dto);
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada con id " + id));
        String codigoNormalizado = dto.getCodigo().trim().toUpperCase();
        if (!carta.getCodigo().equals(codigoNormalizado) && cartaRepository.existsByCodigo(codigoNormalizado)) {
            throw new RuntimeException("El código " + codigoNormalizado + " ya existe");
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
        log.info("Carta actualizada con id: {}", carta.getId());
        return toDTO(carta);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando carta con id: {}", id);
        if (!cartaRepository.existsById(id)) {
            throw new RuntimeException("Carta no encontrada con id " + id);
        }
        cartaRepository.deleteById(id);
        log.info("Carta eliminada con id: {}", id);
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