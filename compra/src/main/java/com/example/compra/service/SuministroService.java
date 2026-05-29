package com.example.compra.service;

import com.example.compra.dto.SuministroDTO;
import com.example.compra.model.Suministro;
import com.example.compra.repository.SuministroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuministroService {

    private final SuministroRepository suministroRepository;
    public List<SuministroDTO> listar() {
        return suministroRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public SuministroDTO obtener(Long id) {
        return suministroRepository.findById(id).map(this::toDTO).orElse(null);
    }
    public Suministro obtenerEntidad(Long id) {
        return suministroRepository.findById(id).orElse(null);
    }
    public SuministroDTO crear(SuministroDTO dto) {
        Suministro s = new Suministro();
        s.setNombre(dto.getNombre());
        s.setCosto(dto.getCosto());
        s.setCantidadCartas(dto.getCantidadCartas());
        s.setProbabilidades(dto.getProbabilidades());
        s = suministroRepository.save(s);
        log.info("Suminitro creado: {}", s.getId());
        return toDTO(s);
    }
    public SuministroDTO actualizar(Long id, SuministroDTO dto) {
        Suministro s = suministroRepository.findById(id).orElse(null);
        if (s == null) {
            log.warn("Suministrio no encontrado: {}", id);
            return null;
        }
        s.setNombre(dto.getNombre());
        s.setCosto(dto.getCosto());
        s.setCantidadCartas(dto.getCantidadCartas());
        s.setProbabilidades(dto.getProbabilidades());
        s = suministroRepository.save(s);
        log.info("Suministro actualizado: {}", id);
        return toDTO(s);
    }
    public void eliminar(Long id) {
        if (!suministroRepository.existsById(id)) {
            log.warn("Suministro no encontrado: {}", id);
            return;
        }
        suministroRepository.deleteById(id);
        log.info("Suministro eliminado: {}", id);
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
