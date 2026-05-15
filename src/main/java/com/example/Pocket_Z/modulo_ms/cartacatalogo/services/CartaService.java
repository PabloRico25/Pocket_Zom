package com.example.Pocket_Z.modulo_ms.cartacatalogo.services;

import com.example.Pocket_Z.modulo_ms.cartacatalogo.model.Carta;
import com.example.Pocket_Z.modulo_ms.cartacatalogo.repository.CartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartaService {
    private final CartaRepository cartaRepository;

    public List<Carta> listar() {
        return cartaRepository.findAll();
    }

    public Optional<Carta> obtener(Long id) {
        return cartaRepository.findById(id);
    }

    public Carta guardar(Carta carta) {
        return cartaRepository.save(carta);
    }

    public void eliminar(Long id) {
        cartaRepository.deleteById(id);
    }
}