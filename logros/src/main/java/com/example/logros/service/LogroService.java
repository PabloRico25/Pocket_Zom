package com.example.logros.service;

import com.example.logros.dto.LogroRequestDTO;
import com.example.logros.dto.LogroResponseDTO;
import com.example.logros.model.Logro;
import com.example.logros.repository.LogroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogroService {
    private final LogroRepository logroRepository;

    public List<LogroResponseDTO> listar() {
        return logroRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public LogroResponseDTO obtener(String id) {
        Logro l = logroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado: " + id));
        return toDTO(l);
    }

    public List<LogroResponseDTO> listarPorTipo(String tipo) {
        return logroRepository.findByCondicionTipo(tipo).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public LogroResponseDTO crear(LogroRequestDTO dto) {
        if (logroRepository.existsById(dto.getIdLogro())) {
            throw new RuntimeException("Ya existe un logro con ID: " + dto.getIdLogro());
        }
        Logro l = new Logro();
        l.setIdLogro(dto.getIdLogro());
        l.setNombre(dto.getNombre());
        l.setDescripcion(dto.getDescripcion());
        l.setCondicionTipo(dto.getCondicionTipo());
        l.setCondicionValor(dto.getCondicionValor());
        l.setRecompensaMonedas(dto.getRecompensaMonedas() != null ? dto.getRecompensaMonedas() : 0);
        l.setRecompensaExp(dto.getRecompensaExp() != null ? dto.getRecompensaExp() : 0);
        Logro saved = logroRepository.save(l);
        log.info("Logro creado: {}", saved.getIdLogro());
        return toDTO(saved);
    }

    @Transactional
    public LogroResponseDTO actualizar(String id, LogroRequestDTO dto) {
        Logro l = logroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado: " + id));
        l.setNombre(dto.getNombre());
        l.setDescripcion(dto.getDescripcion());
        l.setCondicionTipo(dto.getCondicionTipo());
        l.setCondicionValor(dto.getCondicionValor());
        l.setRecompensaMonedas(dto.getRecompensaMonedas() != null ? dto.getRecompensaMonedas() : 0);
        l.setRecompensaExp(dto.getRecompensaExp() != null ? dto.getRecompensaExp() : 0);
        Logro updated = logroRepository.save(l);
        log.info("Logro actualizado: {}", updated.getIdLogro());
        return toDTO(updated);
    }

    @Transactional
    public void eliminar(String id) {
        if (!logroRepository.existsById(id)) {
            throw new RuntimeException("Logro no encontrado: " + id);
        }
        logroRepository.deleteById(id);
        log.info("Logro eliminado: {}", id);
    }

    // Método interno para obtener entidad
    public Logro obtenerEntidad(String id) {
        return logroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado: " + id));
    }

    private LogroResponseDTO toDTO(Logro l) {
        return new LogroResponseDTO(
                l.getIdLogro(),
                l.getNombre(),
                l.getDescripcion(),
                l.getCondicionTipo(),
                l.getCondicionValor(),
                l.getRecompensaMonedas(),
                l.getRecompensaExp()
        );
    }
}