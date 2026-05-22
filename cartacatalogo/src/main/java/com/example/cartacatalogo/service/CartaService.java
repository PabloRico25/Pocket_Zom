package com.example.cartacatalogo.service;

import com.example.cartacatalogo.dto.CartaRequestDTO;
import com.example.cartacatalogo.dto.CartaResponseDTO;
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

    public List<CartaResponseDTO> listar() {
        return cartaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CartaResponseDTO obtenerPorId(Long id) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada con id " + id));
        return convertirADTO(carta);
    }

    public CartaResponseDTO obtenerPorCodigo(String codigo) {
        Carta carta = cartaRepository.findByCodigo(codigo.toUpperCase().trim())
                .orElseThrow(() -> new RuntimeException("Carta no encontrada con código " + codigo));
        return convertirADTO(carta);
    }

    @Transactional
    public CartaResponseDTO crear(CartaRequestDTO dto) {
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
        Carta guardada = cartaRepository.save(carta);
        log.info("Carta creada con id: {}", guardada.getId());
        return convertirADTO(guardada);
    }

    @Transactional
    public CartaResponseDTO actualizar(Long id, CartaRequestDTO dto) {
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
        Carta actualizada = cartaRepository.save(carta);
        log.info("Carta actualizada id: {}", actualizada.getId());
        return convertirADTO(actualizada);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!cartaRepository.existsById(id)) {
            throw new RuntimeException("Carta no encontrada con id " + id);
        }
        cartaRepository.deleteById(id);
        log.info("Carta eliminada id: {}", id);
    }

    private CartaResponseDTO convertirADTO(Carta carta) {
        return new CartaResponseDTO(
                carta.getId(),
                carta.getCodigo(),
                carta.getNombre(),
                carta.getRaza(),
                carta.getAtaque(),
                carta.getDefensa(),
                carta.getCoste(),
                carta.getHabilidad(),
                carta.getActiva()
        );
    }
}