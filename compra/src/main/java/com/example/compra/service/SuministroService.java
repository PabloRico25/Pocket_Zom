package com.example.compra.service;

import com.example.compra.dto.SuministroRequestDTO;
import com.example.compra.dto.SuministroResponseDTO;
import com.example.compra.model.Suministro;
import com.example.compra.repository.SuministroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuministroService {
    private final SuministroRepository suministroRepository;

    public List<SuministroResponseDTO> listar() {
        return suministroRepository.findAll().stream()
                .map(this::convertirADTO).collect(Collectors.toList());
    }

    public SuministroResponseDTO obtener(Long id) {
        Suministro s = suministroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));
        return convertirADTO(s);
    }

    @Transactional
    public SuministroResponseDTO crear(SuministroRequestDTO dto) {
        Suministro s = new Suministro();
        s.setNombre(dto.getNombre());
        s.setCosto(dto.getCosto());
        s.setCantidadCartas(dto.getCantidadCartas());
        s.setProbabilidades(dto.getProbabilidades());
        s = suministroRepository.save(s);
        log.info("Suministro creado: {}", s.getNombre());
        return convertirADTO(s);
    }

    @Transactional
    public SuministroResponseDTO actualizar(Long id, SuministroRequestDTO dto) {
        Suministro s = suministroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));
        s.setNombre(dto.getNombre());
        s.setCosto(dto.getCosto());
        s.setCantidadCartas(dto.getCantidadCartas());
        s.setProbabilidades(dto.getProbabilidades());
        s = suministroRepository.save(s);
        log.info("Suministro actualizado: {}", s.getNombre());
        return convertirADTO(s);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!suministroRepository.existsById(id))
            throw new RuntimeException("Suministro no encontrado");
        suministroRepository.deleteById(id);
        log.info("Suministro eliminado: {}", id);
    }

    // Necesario para obtener la entidad completa
    public Suministro obtenerEntidad(Long id) {
        return suministroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));
    }

    private SuministroResponseDTO convertirADTO(Suministro s) {
        return new SuministroResponseDTO(s.getId(), s.getNombre(), s.getCosto(),
                s.getCantidadCartas(), s.getProbabilidades());
    }
}