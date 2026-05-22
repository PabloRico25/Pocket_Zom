package com.example.billetera.service;

import com.example.billetera.dto.MovimientoRequestDTO;
import com.example.billetera.dto.MovimientoResponseDTO;
import com.example.billetera.model.Cartera;
import com.example.billetera.model.Movimiento;
import com.example.billetera.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final CarteraService carteraService;

    @Transactional
    public MovimientoResponseDTO registrarMovimiento(Long jugadorId, MovimientoRequestDTO dto) {
        Cartera cartera = carteraService.obtenerPorJugador(jugadorId)
                .orElseThrow(() -> new RuntimeException("Cartera no encontrada. Use POST /api/carteras/" + jugadorId + " primero."));

        int cambio = dto.getTipo().equalsIgnoreCase("INGRESO") ? dto.getMonto() : -dto.getMonto();
        int nuevoSaldo = cartera.getSaldo() + cambio;
        if (nuevoSaldo < 0) {
            throw new RuntimeException("Saldo insuficiente. Saldo actual: " + cartera.getSaldo());
        }

        cartera.setSaldo(nuevoSaldo);
        carteraService.guardar(cartera);

        Movimiento mov = new Movimiento();
        mov.setIdTransaccion(UUID.randomUUID().toString());
        mov.setCartera(cartera);   // Usamos la relación @ManyToOne
        mov.setTipo(dto.getTipo().toUpperCase());
        mov.setMonto(dto.getMonto());
        mov.setConcepto(dto.getConcepto());
        mov.setFecha(LocalDateTime.now());

        Movimiento saved = movimientoRepository.save(mov);
        log.info("Movimiento registrado con id: {}", saved.getIdTransaccion());

        return new MovimientoResponseDTO(
                saved.getIdTransaccion(),
                saved.getTipo(),
                saved.getMonto(),
                saved.getConcepto(),
                saved.getFecha()
        );
    }

    public List<MovimientoResponseDTO> listarMovimientos(Long jugadorId) {
        Cartera cartera = carteraService.obtenerPorJugador(jugadorId)
                .orElseThrow(() -> new RuntimeException("Cartera no encontrada"));
        return movimientoRepository.findByCarteraIdOrderByFechaDesc(cartera.getId())
                .stream()
                .map(m -> new MovimientoResponseDTO(
                        m.getIdTransaccion(),
                        m.getTipo(),
                        m.getMonto(),
                        m.getConcepto(),
                        m.getFecha()))
                .collect(Collectors.toList());
    }
}