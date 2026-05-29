package com.example.logros.service;

import com.example.logros.dto.LogroDTO;
import com.example.logros.model.Logro;
import com.example.logros.repository.LogroRepository;
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
public class LogroService {
    @Autowired
    private LogroRepository logroRepository;

    public List<LogroDTO> listar() {
        List<Logro> logros = logroRepository.findAll();
        List<LogroDTO> resultado = new ArrayList<>();
        for (Logro logro : logros) {
            LogroDTO dto = toDTO(logro);
            resultado.add(dto);
        }
        return resultado;
    }

    public LogroDTO obtener(String id) {
        Optional<Logro> optional = logroRepository.findById(id);
        if (optional.isPresent()) {
            Logro logro = optional.get();
            return toDTO(logro);
        } else {
            return null;
        }
    }

    public List<LogroDTO> listarPorTipo(String tipo) {
        List<Logro> logros = logroRepository.findByCondicionTipo(tipo);
        List<LogroDTO> resultado = new ArrayList<>();
        for (Logro logro : logros) {
            LogroDTO dto = toDTO(logro);
            resultado.add(dto);
        }
        return resultado;
    }

    public LogroDTO crear(LogroDTO dto) {
        boolean existe = logroRepository.existsById(dto.getIdLogro());
        if (existe) {
            log.info("Ya existe un logro con ID: " + dto.getIdLogro());
            return null;
        }
        Logro nuevo = new Logro();
        nuevo.setIdLogro(dto.getIdLogro());
        nuevo.setNombre(dto.getNombre());
        nuevo.setDescripcion(dto.getDescripcion());
        nuevo.setCondicionTipo(dto.getCondicionTipo());
        nuevo.setCondicionValor(dto.getCondicionValor());
        int recompensaMonedas = (dto.getRecompensaMonedas() != null) ? dto.getRecompensaMonedas() : 0;
        nuevo.setRecompensaMonedas(recompensaMonedas);
        int recompensaExp = (dto.getRecompensaExp() != null) ? dto.getRecompensaExp() : 0;
        nuevo.setRecompensaExp(recompensaExp);
        Logro guardado = logroRepository.save(nuevo);

        log.info("Logro creado con ID: " + guardado.getIdLogro());
        return toDTO(guardado);
    }

    public LogroDTO actualizar(String id, LogroDTO dto) {
        Optional<Logro> optional = logroRepository.findById(id);
        if (!optional.isPresent()) {

            log.info("No se encontró el logro con ID: " + id);
            return null;
        }
        Logro logro = optional.get();
        logro.setNombre(dto.getNombre());
        logro.setDescripcion(dto.getDescripcion());
        logro.setCondicionTipo(dto.getCondicionTipo());
        logro.setCondicionValor(dto.getCondicionValor());
        int recompensaMonedas = (dto.getRecompensaMonedas() != null) ? dto.getRecompensaMonedas() : 0;
        logro.setRecompensaMonedas(recompensaMonedas);
        int recompensaExp = (dto.getRecompensaExp() != null) ? dto.getRecompensaExp() : 0;
        logro.setRecompensaExp(recompensaExp);
        Logro actualizado = logroRepository.save(logro);
        log.info("Logro actualizado: " + id);
        return toDTO(actualizado);
    }

    public void eliminar(String id) {
        boolean existe = logroRepository.existsById(id);
        if (!existe) {
            log.info("No se encontró el logro con ID: " + id);
            return;
        }
        logroRepository.deleteById(id);

        log.info("Logro eliminado: " + id);
    }

    public Logro obtenerEntidad(String id) {
        Optional<Logro> optional = logroRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        log.info("No se encontró el logro con ID: " + id);
        return null;
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
