package com.example.Pocket_Z.modulo_ms.billetera.services;

import com.example.Pocket_Z.modulo_ms.billetera.model.Billetera;
import com.example.Pocket_Z.modulo_ms.billetera.repository.BilleteraRepository;
import com.example.Pocket_Z.modulo_ms.billetera.repository.CarteraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BilleteraService {
    private final BilleteraRepository billeteraRepository;
    private final CarteraRepository carteraRepository;

    public Billetera crearBilletera(Long idJugador){
        Billetera billetera = new Billetera();
        billetera.setIdJugador(idJugador);
        billetera.setSaldo(0);
        billetera.setMonedasJuego(0);
        return billeteraRepository.save(billetera);
    }

    public void eliminar(Long idBilletera){
        carteraRepository.deleteById(idBilletera);
    }
}
