package com.example.inventario.service;

import com.example.inventario.cliente.CartaClient;
import com.example.inventario.cliente.PerfilClient;
import com.example.inventario.dto.CartaUsuarioDTO;
import com.example.inventario.model.CartaUsuario;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.CartaUsuarioRepository;
import com.example.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaUsuarioService {
    private final CartaUsuarioRepository cartaUsuarioRepository;
    private final InventarioRepository inventarioRepository;
    private final InventarioService inventarioService;
    private final PerfilClient perfilClient;
    private final CartaClient cartaClient;

    @Transactional
    public CartaUsuarioDTO agregarCarta(Long jugadorId, CartaUsuarioDTO dto) {
        // Validar que el jugador existe
        if (!perfilClient.existeJugador(jugadorId)) {
            throw new RuntimeException("El jugador " + jugadorId + " no existe");
        }
        // Validar que la carta existe
        if (!cartaClient.existeCarta(dto.getCodigoCarta())) {
            throw new RuntimeException("La carta con código " + dto.getCodigoCarta() + " no existe");
        }
        // Obtener inventario del jugador
        Inventario inventario = inventarioService.obtenerEntidad(jugadorId);
        String codigoNormalizado = dto.getCodigoCarta().trim().toUpperCase();

        // Buscar si ya tiene esa carta
        CartaUsuario existente = cartaUsuarioRepository.findByInventarioIdAndCodigoCarta(inventario.getId(), codigoNormalizado)
                .orElse(null);
        if (existente != null) {
            // Actualizar cantidad
            existente.setCantidad(existente.getCantidad() + dto.getCantidad());
            if (dto.getEsFavorita() != null) {
                existente.setEsFavorita(dto.getEsFavorita());
            }
            existente = cartaUsuarioRepository.save(existente);
            log.info("Cantidad actualizada para carta {} en inventario {}", codigoNormalizado, inventario.getId());
            return toDTO(existente);
        } else {
            // Crear nuevo registro
            CartaUsuario nueva = new CartaUsuario();
            nueva.setInventarioId(inventario.getId());
            nueva.setCodigoCarta(codigoNormalizado);
            nueva.setCantidad(dto.getCantidad());
            nueva.setEsFavorita(dto.getEsFavorita() != null ? dto.getEsFavorita() : false);
            nueva.setFechaAdquisicion(LocalDateTime.now());
            nueva = cartaUsuarioRepository.save(nueva);
            log.info("Carta {} añadida al inventario {}", codigoNormalizado, inventario.getId());
            return toDTO(nueva);
        }
    }

    @Transactional
    public void quitarCarta(Long jugadorId, String codigoCarta, Integer cantidad) {
        Inventario inventario = inventarioService.obtenerEntidad(jugadorId);
        String codigoNormalizado = codigoCarta.trim().toUpperCase();
        CartaUsuario carta = cartaUsuarioRepository.findByInventarioIdAndCodigoCarta(inventario.getId(), codigoNormalizado)
                .orElseThrow(() -> new RuntimeException("La carta " + codigoNormalizado + " no está en el inventario"));
        int nuevaCantidad = carta.getCantidad() - cantidad;
        if (nuevaCantidad <= 0) {
            cartaUsuarioRepository.delete(carta);
            log.info("Carta {} eliminada del inventario {}", codigoNormalizado, inventario.getId());
        } else {
            carta.setCantidad(nuevaCantidad);
            cartaUsuarioRepository.save(carta);
            log.info("Cantidad de {} reducida a {} en inventario {}", codigoNormalizado, nuevaCantidad, inventario.getId());
        }
    }

    public List<CartaUsuarioDTO> listarCartas(Long jugadorId) {
        Inventario inventario = inventarioService.obtenerEntidad(jugadorId);
        return cartaUsuarioRepository.findByInventarioId(inventario.getId())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Método de transferencia como lo pediste
    @Transactional
    public void transferirCarta(Long jugadorOrigenId, Long jugadorDestinoId, String codigoCarta, Integer cantidad) {
        // Quitar del origen
        quitarCarta(jugadorOrigenId, codigoCarta, cantidad);
        // Obtener inventario destino (validación explícita)
        Inventario inventarioDestino = inventarioRepository.findByJugadorId(jugadorDestinoId)
                .orElseThrow(() -> new RuntimeException("El jugador destino no tiene inventario"));
        // Añadir al destino
        CartaUsuarioDTO dto = new CartaUsuarioDTO();
        dto.setCodigoCarta(codigoCarta);
        dto.setCantidad(cantidad);
        agregarCarta(jugadorDestinoId, dto);
        log.info("Transferida carta {} desde jugador {} a jugador {}", codigoCarta, jugadorOrigenId, jugadorDestinoId);
    }

    private CartaUsuarioDTO toDTO(CartaUsuario cu) {
        CartaUsuarioDTO dto = new CartaUsuarioDTO();
        dto.setId(cu.getId());
        dto.setCodigoCarta(cu.getCodigoCarta());
        dto.setCantidad(cu.getCantidad());
        dto.setEsFavorita(cu.getEsFavorita());
        dto.setFechaAdquisicion(cu.getFechaAdquisicion());
        return dto;
    }
}