package com.example.compra.service;

import com.example.compra.dto.SuministroDTO;
import com.example.compra.model.Suministro;
import com.example.compra.repository.SuministroRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class SuministroService {
    @Autowired
    private SuministroRepository suministroRepository;

    public List<SuministroDTO> listar() {
        List<Suministro> suministros = suministroRepository.findAll();
        List<SuministroDTO> resultado = new ArrayList<>();
        for (Suministro suministro : suministros) {
            SuministroDTO dto = toDTO(suministro);
            resultado.add(dto);
        }
        return resultado;
    }

    public SuministroDTO obtener(Long id) {
        Optional<Suministro> optional = suministroRepository.findById(id);
        if (optional.isPresent()) {
            Suministro suministro = optional.get();
            return toDTO(suministro);
        } else {
            return null;
        }
    }

    public SuministroDTO crear(SuministroDTO dto) {
        Suministro s = new Suministro();
        s.setNombre(dto.getNombre());
        s.setCosto(dto.getCosto());
        s.setCantidadCartas(dto.getCantidadCartas());
        s.setProbabilidades(dto.getProbabilidades());
        s = suministroRepository.save(s);
        return toDTO(s);
    }

    public SuministroDTO actualizar(Long id, SuministroDTO dto) {
        Optional<Suministro> optional = suministroRepository.findById(id);
        if (!optional.isPresent()) {
            log.info("No se encontró el suministro con ID: " + id);
            return null;
        }
        Suministro suministro = optional.get();
        suministro.setNombre(dto.getNombre());
        suministro.setCosto(dto.getCosto());
        suministro.setCantidadCartas(dto.getCantidadCartas());
        suministro.setProbabilidades(dto.getProbabilidades());
        Suministro actualizado = suministroRepository.save(suministro);
        log.info("Suministro actualizado: " + id);
        return toDTO(actualizado);
    }

    public void eliminar(Long id) {
        boolean existe = suministroRepository.existsById(id);
        if (!existe) {
            log.info("No se encontró el suministro con ID: " + id);
            return;
        }
        suministroRepository.deleteById(id);
        log.info("Suministro eliminado: " + id);
    }

    public Suministro obtenerEntidad(Long id) {
        Optional<Suministro> optional = suministroRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        log.info("No se encontró el suministro con ID: " + id);
        return null;
    }

    private SuministroDTO toDTO(Suministro s) {
        SuministroDTO dto = new SuministroDTO();
        dto.setId(s.getId());
        dto.setNombre(s.getNombre());
        dto.setCosto(s.getCosto());
        dto.setCantidadCartas(s.getCantidadCartas());
        dto.setProbabilidades(s.getProbabilidades());
        return dto;
    }
}
