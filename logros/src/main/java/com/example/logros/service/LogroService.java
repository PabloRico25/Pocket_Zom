package com.example.logros.service;

import com.example.logros.dto.LogroDTO;
import com.example.logros.model.Logro;
import com.example.logros.repository.LogroRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class LogroService {
    @Autowired
    private LogroRepository logroRepository;

    public List<LogroDTO> listar() {
        return logroRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public LogroDTO obtener(String id) {
        return logroRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado"));
    }

    public List<LogroDTO> listarPorTipo(String tipo) {
        return logroRepository.findByCondicionTipo(tipo).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public LogroDTO crear(LogroDTO dto) {
        if (logroRepository.existsById(dto.getIdLogro())) {
            throw new RuntimeException("Ya existe un logro con ID " + dto.getIdLogro());
        }
        Logro l = new Logro();
        l.setIdLogro(dto.getIdLogro());
        l.setNombre(dto.getNombre());
        l.setDescripcion(dto.getDescripcion());
        l.setCondicionTipo(dto.getCondicionTipo());
        l.setCondicionValor(dto.getCondicionValor());
        l.setRecompensaMonedas(dto.getRecompensaMonedas() != null ? dto.getRecompensaMonedas() : 0);
        l.setRecompensaExp(dto.getRecompensaExp() != null ? dto.getRecompensaExp() : 0);
        l = logroRepository.save(l);
        return toDTO(l);
    }

    public LogroDTO actualizar(String id, LogroDTO dto) {
        Logro l = logroRepository.findById(id).orElseThrow(() -> new RuntimeException("Logro no encontrado"));
        l.setNombre(dto.getNombre());
        l.setDescripcion(dto.getDescripcion());
        l.setCondicionTipo(dto.getCondicionTipo());
        l.setCondicionValor(dto.getCondicionValor());
        l.setRecompensaMonedas(dto.getRecompensaMonedas() != null ? dto.getRecompensaMonedas() : 0);
        l.setRecompensaExp(dto.getRecompensaExp() != null ? dto.getRecompensaExp() : 0);
        l = logroRepository.save(l);
        return toDTO(l);
    }

    public void eliminar(String id) {
        if (!logroRepository.existsById(id)) throw new RuntimeException("Logro no encontrado");
        logroRepository.deleteById(id);
    }

    public Logro obtenerEntidad(String id) {
        return logroRepository.findById(id).orElseThrow(() -> new RuntimeException("Logro no encontrado"));
    }

    private LogroDTO toDTO(Logro l) {
        LogroDTO dto = new LogroDTO();
        dto.setIdLogro(l.getIdLogro());
        dto.setNombre(l.getNombre());
        dto.setDescripcion(l.getDescripcion());
        dto.setCondicionTipo(l.getCondicionTipo());
        dto.setCondicionValor(l.getCondicionValor());
        dto.setRecompensaMonedas(l.getRecompensaMonedas());
        dto.setRecompensaExp(l.getRecompensaExp());
        return dto;
    }
}
