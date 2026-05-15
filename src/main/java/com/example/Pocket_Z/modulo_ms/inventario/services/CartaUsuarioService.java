package com.example.Pocket_Z.modulo_ms.inventario.services;

import com.example.Pocket_Z.modulo_ms.inventario.model.CartaUsuario;
import com.example.Pocket_Z.modulo_ms.inventario.model.Inventario;
import com.example.Pocket_Z.modulo_ms.inventario.repository.CartaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartaUsuarioService {
    private final CartaUsuarioRepository cartaUsuarioRepository;
    private final InventarioService inventarioService;

    public List<CartaUsuario> listarPorJugador(Long jugadorId) {
        return cartaUsuarioRepository.findByInventario_JugadorId(jugadorId);
    }

    public Optional<CartaUsuario> obtener(Long id) {
        return cartaUsuarioRepository.findById(id);
    }

    @Transactional
    public CartaUsuario agregarCarta(Long jugadorId, String codigoCarta, int cantidad) {
        Inventario inventario = inventarioService.obtenerPorJugador(jugadorId)
                .orElseGet(() -> inventarioService.crearInventario(jugadorId));

        Optional<CartaUsuario> existente = cartaUsuarioRepository
                .findByInventarioIdAndCodigoCarta(inventario.getId(), codigoCarta);

        if (existente.isPresent()) {
            CartaUsuario c = existente.get();
            c.setCantidad(c.getCantidad() + cantidad);
            return cartaUsuarioRepository.save(c);
        } else {
            CartaUsuario nueva = new CartaUsuario();
            nueva.setInventario(inventario);
            nueva.setCodigoCarta(codigoCarta);
            nueva.setCantidad(cantidad);
            return cartaUsuarioRepository.save(nueva);
        }
    }

    @Transactional
    public void eliminarCarta(Long id) {
        cartaUsuarioRepository.deleteById(id);
    }

    public void marcarFavorita(Long id, boolean favorita) {
        cartaUsuarioRepository.findById(id).ifPresent(c -> {
            c.setEsFavorita(favorita);
            cartaUsuarioRepository.save(c);
        });
    }
}