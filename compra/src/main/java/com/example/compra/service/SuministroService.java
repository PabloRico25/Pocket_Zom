package com.example.compra.service;

import com.example.compra.dto.SuministroDTO;
import com.example.compra.model.Suministro;
import com.example.compra.repository.SuministroRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class SuministroService {
    @Autowired
    private SuministroRepository suministroRepository;

    public List<SuministroDTO> listar() {
        return suministroRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public SuministroDTO obtener(Long id) {
        return suministroRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));
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
        Suministro s = suministroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));
        s.setNombre(dto.getNombre());
        s.setCosto(dto.getCosto());
        s.setCantidadCartas(dto.getCantidadCartas());
        s.setProbabilidades(dto.getProbabilidades());
        s = suministroRepository.save(s);
        return toDTO(s);
    }

    public void eliminar(Long id) {
        if (!suministroRepository.existsById(id)) throw new RuntimeException("Suministro no encontrado");
        suministroRepository.deleteById(id);
    }

    public Suministro obtenerEntidad(Long id) {
        return suministroRepository.findById(id).orElseThrow(() -> new RuntimeException("Suministro no encontrado"));
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
