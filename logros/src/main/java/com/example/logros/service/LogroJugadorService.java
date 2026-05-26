package com.example.logros.service;

import com.example.logros.client.BilleteraClient;
import com.example.logros.dto.LogroJugadorDTO;
import com.example.logros.model.Logro;
import com.example.logros.model.LogroJugador;
import com.example.logros.repository.LogroJugadorRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return logroJugadorRepository.findByJugadorId(jugadorId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public boolean yaDesbloqueado(Long jugadorId, String idLogro) {
        return logroJugadorRepository.findByJugadorIdAndIdLogro(jugadorId, idLogro).isPresent();
    }

    public LogroJugadorDTO desbloquear(Long jugadorId, String idLogro) {
        if (yaDesbloqueado(jugadorId, idLogro)) {
            throw new RuntimeException("El logro ya fue desbloqueado por este jugador");
        }
        Logro logro = logroService.obtenerEntidad(idLogro);
        LogroJugador lj = new LogroJugador();
        lj.setJugadorId(jugadorId);
        lj.setIdLogro(idLogro);
        lj.setFechaDesbloqueo(LocalDateTime.now());
        lj = logroJugadorRepository.save(lj);

        // Otorgar recompensa (monedas) al jugador
        if (logro.getRecompensaMonedas() > 0) {
            try {
                billeteraClient.registrarMovimiento(jugadorId, "INGRESO", logro.getRecompensaMonedas(),
                        "Logro desbloqueado: " + logro.getNombre());
            } catch (Exception e) {
                log.error("Error al entregar recompensa: {}", e.getMessage());
            }
        }
        // Si hubiera recompensa de experiencia, se podría enviar a otro MS (ej. perfil)
        return toDTO(lj, logro);
    }

    public List<LogroJugadorDTO> verificarYDesbloquear(Long jugadorId, String condicionTipo, Integer valorActual) {
        List<Logro> posibles = logroService.listarPorTipo(condicionTipo).stream()
                .map(dto -> logroService.obtenerEntidad(dto.getIdLogro()))
                .collect(Collectors.toList());

        return posibles.stream()
                .filter(logro -> valorActual >= logro.getCondicionValor())
                .filter(logro -> !yaDesbloqueado(jugadorId, logro.getIdLogro()))
                .map(logro -> desbloquear(jugadorId, logro.getIdLogro()))
                .collect(Collectors.toList());
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
        return toDTO(lj, logro);
    }
}
