package com.example.cartacatalogo.service;

import com.example.cartacatalogo.model.Carta;
import com.example.cartacatalogo.repository.CartaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaService {
    private final CartaRepository cartaRepository;
    public List<Carta> listar() {
        return cartaRepository.findAll();
    }
    public Carta obtenerPorId(Long id) {
        return cartaRepository.findById(id).orElse(null);
    }
    public Carta obtenerPorCodigo(String codigo) {
        return cartaRepository.findByCodigo(codigo.trim().toUpperCase()).orElse(null);
    }
    public boolean existePorCodigo(String codigo) {
        return cartaRepository.existsByCodigo(codigo.trim().toUpperCase());
    }
    @Transactional
    public Carta crear(Carta carta) {
        if (cartaRepository.existsByCodigo(carta.getCodigo().trim().toUpperCase())) {log.warn("Intento de crear carta con código duplicado: {}", carta.getCodigo());
            return null;
        }
        carta.setCodigo(carta.getCodigo().trim().toUpperCase());
        if (carta.getAtaque() == null) carta.setAtaque(0);
        if (carta.getDefensa() == null) carta.setDefensa(0);
        if (carta.getCoste() == null) carta.setCoste(0);
        if (carta.getActiva() == null) carta.setActiva(true);
        Carta saved = cartaRepository.save(carta);log.info("Carta creada: {} ({})", saved.getNombre(), saved.getCodigo());
        return saved;
    }
    @Transactional
    public Carta actualizar(Long id, Carta cartaActualizada) {
        Carta existente = cartaRepository.findById(id).orElse(null);
        if (existente == null) return null;
        String nuevoCodigo = cartaActualizada.getCodigo().trim().toUpperCase();
        if (!existente.getCodigo().equals(nuevoCodigo) && cartaRepository.existsByCodigo(nuevoCodigo)) {log.warn("Intento de actualizar a código duplicado: {}", nuevoCodigo);
            return null;
        }
        existente.setCodigo(nuevoCodigo);
        existente.setNombre(cartaActualizada.getNombre());
        existente.setRaza(cartaActualizada.getRaza());
        existente.setAtaque(cartaActualizada.getAtaque());
        existente.setDefensa(cartaActualizada.getDefensa());
        existente.setCoste(cartaActualizada.getCoste());
        existente.setHabilidad(cartaActualizada.getHabilidad());
        if (cartaActualizada.getActiva() != null) existente.setActiva(cartaActualizada.getActiva());
        Carta saved = cartaRepository.save(existente);log.info("Carta actualizada: {}", saved.getCodigo());
        return saved;
    }
    @Transactional
    public boolean eliminar(Long id) {
        if (!cartaRepository.existsById(id)) return false;
        cartaRepository.deleteById(id);log.info("Carta eliminada id: {}", id);
        return true;
    }
    public List<Carta> devolverListaDecartas(Integer coste){log.info("Consultando cartas de coste {}",coste);
        return cartaRepository.findByCoste(coste);
    }
}