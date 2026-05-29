package com.example.logros.service;

import com.example.logros.client.BilleteraClient;
import com.example.logros.dto.LogroDTO;
import com.example.logros.dto.LogroJugadorDTO;
import com.example.logros.model.Logro;
import com.example.logros.model.LogroJugador;
import com.example.logros.repository.LogroJugadorRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class LogroJugadorService {
    @Autowired
    private LogroJugadorRepository logroJugadorRepository;
    @Autowired
    private LogroService logroService;
    @Autowired
    private BilleteraClient billeteraClient;

    public List<LogroJugadorDTO> listarPorJugador(Long jugadorId) {
        List<LogroJugador> logros = logroJugadorRepository.findByJugadorId(jugadorId);

        List<LogroJugadorDTO> resultado = new ArrayList<>();
        for (LogroJugador logro : logros) {
            LogroJugadorDTO dto = toDTO(logro);
            resultado.add(dto);
        }
        return resultado;
    }

    public boolean yaDesbloqueado(Long jugadorId, String idLogro) {
        return logroJugadorRepository.findByJugadorIdAndIdLogro(jugadorId, idLogro).isPresent();
    }

    public LogroJugadorDTO desbloquear(Long jugadorId, String idLogro) {
        if (yaDesbloqueado(jugadorId, idLogro)) {
            log.info("El logro " + idLogro + " ya fue desbloqueado por el jugador " + jugadorId);
            return null;
        }
        Logro logro = logroService.obtenerEntidad(idLogro);
        if (logro == null) {
            log.info("No se encontró el logro con ID: " + idLogro);
            return null;
        }
        LogroJugador lj = new LogroJugador();
        lj.setJugadorId(jugadorId);
        lj.setIdLogro(idLogro);
        lj.setFechaDesbloqueo(LocalDateTime.now());
        LogroJugador guardado = logroJugadorRepository.save(lj);
        if (logro.getRecompensaMonedas() > 0) {
            try {
                billeteraClient.registrarMovimiento(jugadorId,"INGRESO",logro.getRecompensaMonedas(), "Logro desbloqueado: " + logro.getNombre());

                log.info("Se entregó recompensa de " + logro.getRecompensaMonedas() + " monedas al jugador " + jugadorId);

            } catch (Exception e) {

                log.info("Error al entregar recompensa: " + e.getMessage());
            }
        }
        return toDTO(guardado, logro);
    }

    public List<LogroJugadorDTO> verificarYDesbloquear(Long jugadorId, String condicionTipo, Integer valorActual) {
        List<LogroDTO> logrosDTO = logroService.listarPorTipo(condicionTipo);
        List<Logro> posibles = new ArrayList<>();
        for (LogroDTO dto : logrosDTO) {
            Logro logro = logroService.obtenerEntidad(dto.getIdLogro());
            posibles.add(logro);
        }
        List<LogroJugadorDTO> resultado = new ArrayList<>();
        for (Logro logro : posibles) {
            if (valorActual >= logro.getCondicionValor()) {
                if (!yaDesbloqueado(jugadorId, logro.getIdLogro())) {
                    LogroJugadorDTO desbloqueado = desbloquear(jugadorId, logro.getIdLogro());
                    if (desbloqueado != null) {
                        resultado.add(desbloqueado);
                    }
                }
            }
        }
        return resultado;
    }

    private LogroJugadorDTO toDTO(LogroJugador lj, Logro logro) {
        LogroJugadorDTO dto = new LogroJugadorDTO();
        dto.setId(lj.getId());
        dto.setJugadorId(lj.getJugadorId());
        dto.setIdLogro(lj.getIdLogro());
        dto.setNombreLogro(logro != null ? logro.getNombre() : "");
        dto.setFechaDesbloqueo(lj.getFechaDesbloqueo());
        return dto;
    }

    private LogroJugadorDTO toDTO(LogroJugador lj) {
        Logro logro = logroService.obtenerEntidad(lj.getIdLogro());
        if (logro == null) {
            return null;
        }
        return toDTO(lj, logro);
    }
}
