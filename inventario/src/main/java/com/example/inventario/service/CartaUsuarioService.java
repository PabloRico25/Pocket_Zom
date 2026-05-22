package com.example.inventario.service;

import com.example.inventario.dto.CartaUsuarioRequestDTO;
import com.example.inventario.dto.CartaUsuarioResponseDTO;
import com.example.inventario.model.CartaUsuario;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.CartaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaUsuarioService {
    private final CartaUsuarioRepository cartaUsuarioRepository;
    private final InventarioService inventarioService;

    @Transactional
    public CartaUsuarioResponseDTO agregarCarta(Long jugadorId, CartaUsuarioRequestDTO dto) {
        // Buscar inventario del jugador
        Inventario inventario = inventarioService.obtenerPorJugador(jugadorId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado. Use POST /api/inventarios/" + jugadorId + " primero."));

        String codigoNormalizado = dto.getCodigoCarta().trim().toUpperCase();

        // Buscar si ya existe esa carta en el inventario
        Optional<CartaUsuario> existenteOpt = cartaUsuarioRepository
                .findByInventarioIdAndCodigoCarta(inventario.getId(), codigoNormalizado);

        if (existenteOpt.isPresent()) {
            // Si ya existe, incrementamos la cantidad
            CartaUsuario existente = existenteOpt.get();
            existente.setCantidad(existente.getCantidad() + dto.getCantidad());
            if (dto.getEsFavorita() != null) {
                existente.setEsFavorita(dto.getEsFavorita());
            }
            CartaUsuario actualizada = cartaUsuarioRepository.save(existente);
            log.info("Cantidad actualizada para carta {} en inventario {}", codigoNormalizado, inventario.getId());
            return convertirADTO(actualizada);
        } else {
            // Si no existe, creamos un nuevo registro
            CartaUsuario nueva = new CartaUsuario();
            nueva.setInventario(inventario);
            nueva.setCodigoCarta(codigoNormalizado);
            nueva.setCantidad(dto.getCantidad());
            nueva.setEsFavorita(dto.getEsFavorita() != null ? dto.getEsFavorita() : false);
            nueva.setFechaAdquisicion(LocalDateTime.now());
            CartaUsuario guardada = cartaUsuarioRepository.save(nueva);
            log.info("Carta {} añadida al inventario {}", codigoNormalizado, inventario.getId());
            return convertirADTO(guardada);
        }
    }

    @Transactional
    public void quitarCarta(Long jugadorId, String codigoCarta, Integer cantidad) {
        Inventario inventario = inventarioService.obtenerPorJugador(jugadorId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        String codigoNormalizado = codigoCarta.trim().toUpperCase();
        CartaUsuario carta = cartaUsuarioRepository
                .findByInventarioIdAndCodigoCarta(inventario.getId(), codigoNormalizado)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada en el inventario"));
        int nuevaCantidad = carta.getCantidad() - cantidad;
        if (nuevaCantidad <= 0) {
            cartaUsuarioRepository.delete(carta);
            log.info("Carta {} eliminada del inventario", codigoNormalizado);
        } else {
            carta.setCantidad(nuevaCantidad);
            cartaUsuarioRepository.save(carta);
            log.info("Cantidad de {} reducida a {}", codigoNormalizado, nuevaCantidad);
        }
    }

    public List<CartaUsuarioResponseDTO> listarCartas(Long jugadorId) {
        Inventario inventario = inventarioService.obtenerPorJugador(jugadorId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        return cartaUsuarioRepository.findByInventarioId(inventario.getId())
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private CartaUsuarioResponseDTO convertirADTO(CartaUsuario c) {
        return new CartaUsuarioResponseDTO(
                c.getId(),
                c.getCodigoCarta(),
                c.getCantidad(),
                c.getFechaAdquisicion(),
                c.getEsFavorita()
        );
    }
}