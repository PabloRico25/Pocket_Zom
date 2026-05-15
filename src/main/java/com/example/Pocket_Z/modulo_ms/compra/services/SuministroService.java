package com.example.Pocket_Z.modulo_ms.compra.services;

import com.example.Pocket_Z.modulo_ms.compra.model.Suministro;
import com.example.Pocket_Z.modulo_ms.compra.repository.SuministroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuministroService {
    private final SuministroRepository suministroRepository;

    public List<Suministro> listar() {
        return suministroRepository.findAll();
    }

    public Suministro guardar(Suministro suministro) {
        return suministroRepository.save(suministro);
    }

    public void eliminar(Long id) {
        suministroRepository.deleteById(id);
    }
}
