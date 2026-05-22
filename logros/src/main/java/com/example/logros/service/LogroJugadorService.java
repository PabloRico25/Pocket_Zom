package com.example.logros.service;
import com.example.logros.model.Logro;
import com.example.logros.dto.LogroJugadorRequestDTO;
import com.example.logros.dto.LogroJugadorResponseDTO;
import com.example.logros.model.LogroJugador;
import com.example.logros.repository.LogroJugadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogroJugadorService {
    private final LogroJugadorRepository logroJugadorRepository;
    private final LogroService logroService;

    public List<LogroJugadorResponseDTO> listarPorJugador(String jugadorId) {
        return logroJugadorRepository.findByJugadorId(jugadorId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public boolean yaDesbloqueado(String jugadorId, String idLogro) {
        return logroJugadorRepository.findByJugadorIdAndLogro_IdLogro(jugadorId, idLogro).isPresent();
    }

    @Transactional
    public LogroJugadorResponseDTO desbloquear(LogroJugadorRequestDTO dto) {
        if (yaDesbloqueado(dto.getJugadorId(), dto.getIdLogro())) {
            throw new RuntimeException("El logro ya fue desbloqueado por este jugador");
        }
        var logro = logroService.obtenerEntidad(dto.getIdLogro());
        LogroJugador lj = new LogroJugador();
        lj.setJugadorId(dto.getJugadorId());
        lj.setLogro(logro);
        lj.setFechaDesbloqueo(LocalDateTime.now());
        LogroJugador saved = logroJugadorRepository.save(lj);
        log.info("Logro {} desbloqueado por jugador {}", dto.getIdLogro(), dto.getJugadorId());
        // Aquí se podría enviar un evento Kafka o llamar a billetera para dar recompensa
        return toDTO(saved);
    }

    // Método para verificar y desbloquear automáticamente según progreso
    @Transactional
    public List<LogroJugadorResponseDTO> verificarYDesbloquear(String jugadorId, String tipoCondicion, Integer valorActual) {
        List<Logro> posibles = logroService.listarPorTipo(tipoCondicion).stream()
                .map(dto -> logroService.obtenerEntidad(dto.getIdLogro()))
                .collect(Collectors.toList());

        return posibles.stream()
                .filter(logro -> valorActual >= logro.getCondicionValor())
                .filter(logro -> !yaDesbloqueado(jugadorId, logro.getIdLogro()))
                .map(logro -> {
                    LogroJugadorRequestDTO req = new LogroJugadorRequestDTO();
                    req.setJugadorId(jugadorId);
                    req.setIdLogro(logro.getIdLogro());
                    return desbloquear(req);
                })
                .collect(Collectors.toList());
    }

    private LogroJugadorResponseDTO toDTO(LogroJugador lj) {
        return new LogroJugadorResponseDTO(
                lj.getId(),
                lj.getJugadorId(),
                lj.getLogro().getIdLogro(),
                lj.getLogro().getNombre(),
                lj.getFechaDesbloqueo()
        );
    }
}