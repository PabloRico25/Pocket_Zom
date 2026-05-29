package com.example.inventario.service;

import com.example.inventario.cliente.CartaCliente;
import com.example.inventario.cliente.PerfilCliente;
import com.example.inventario.dto.AgregarCartaDTO;
import com.example.inventario.dto.TransferirCartaDTO;
import com.example.inventario.model.CartaUsuario;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.CartaUsuarioRepository;
import com.example.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaUsuarioService {
    private final CartaUsuarioRepository cartaUsuarioRepository;
    private final InventarioRepository inventarioRepository;
    private final PerfilCliente perfilClient;
    private final CartaCliente cartaClient;

    public List<CartaUsuario> listarCartas(Long idJugador) {
        Inventario inv = inventarioRepository.findByIdJugador(idJugador).orElse(null);
        if (inv == null) return null;
        return cartaUsuarioRepository.findByIdInventario(inv.getIdInventario());
    }

    public boolean tieneCarta(Long idJugador, String codigoCarta, Integer cantidad) {
        Inventario inv = inventarioRepository.findByIdJugador(idJugador).orElse(null);
        if (inv == null) return false;
        CartaUsuario cu = cartaUsuarioRepository.findByIdInventarioAndCodigoCarta(inv.getIdInventario(), codigoCarta.trim().toUpperCase()).orElse(null);
        if (cu == null) return false;
        int necesita = cantidad != null ? cantidad : 1;
        return cu.getCantidad() >= necesita;
    }
    public CartaUsuario agregar(Long idJugador, String codigoCarta, Integer cantidad) {
        if (!Boolean.TRUE.equals(perfilClient.existeJugador(idJugador))) {

            log.warn("Jugador {} no existe", idJugador);
            return null;
        }
        if (!Boolean.TRUE.equals(cartaClient.existeCarta(codigoCarta))) {

            log.warn("Carta {} no existe en catálogo", codigoCarta);
            return null;
        }
        Inventario inv = inventarioRepository.findByIdJugador(idJugador).orElse(null);
        if (inv == null) {

            log.warn("El jugador {} no tiene inventario", idJugador);
            return null;
        }
        String codigo = codigoCarta.trim().toUpperCase();
        CartaUsuario existente = cartaUsuarioRepository.findByIdInventarioAndCodigoCarta(inv.getIdInventario(), codigo).orElse(null);
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);

            log.info("Carta {} actualizada en inventario de jugador {}", codigo, idJugador);
            return cartaUsuarioRepository.save(existente);
        }
        CartaUsuario nueva = new CartaUsuario();
        nueva.setIdInventario(inv.getIdInventario());
        nueva.setCodigoCarta(codigo);
        nueva.setCantidad(cantidad);

        log.info("Carta {} añadida al inventario de jugador {}", codigo, idJugador);
        return cartaUsuarioRepository.save(nueva);
    }
    public boolean quitar(Long idJugador, String codigoCarta, Integer cantidad) {Inventario inv = inventarioRepository.findByIdJugador(idJugador).orElse(null);
        if (inv == null) return false;
        String codigo = codigoCarta.trim().toUpperCase();
        CartaUsuario cu = cartaUsuarioRepository.findByIdInventarioAndCodigoCarta(inv.getIdInventario(), codigo).orElse(null);
        if (cu == null) return false;
        int nuevaCantidad = cu.getCantidad() - cantidad;
        if (nuevaCantidad <= 0) {
            cartaUsuarioRepository.delete(cu);
        } else {
            cu.setCantidad(nuevaCantidad);
            cartaUsuarioRepository.save(cu);
        }

        log.info("Carta {} quitada del inventario de jugador {}", codigo, idJugador);
        return true;
    }
    public boolean transferir(Long idOrigen, Long idDestino, String codigoCarta, Integer cantidad) {
        boolean quitada = quitar(idOrigen, codigoCarta, cantidad);
        if (!quitada) {

            log.warn("No se pudo quitar carta {} del jugador {}", codigoCarta, idOrigen);
            return false;
        }
        CartaUsuario agregada = agregar(idDestino, codigoCarta, cantidad);
        if (agregada == null) {

            log.warn("No se pudo agregar carta {} al jugador {}", codigoCarta, idDestino);
            return false;
        }

        log.info("Carta {} transferida de jugador {} a {}", codigoCarta, idOrigen, idDestino);
        return true;
    }
    
}
