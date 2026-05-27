package com.example.billetera.service;

import com.example.billetera.cliente.PerfilClient;
import com.example.billetera.dto.MovimientoDTO;
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
    private final PerfilClient perfilClient;

    @Transactional
    public MovimientoDTO registrarMovimiento(Long jugadorId, MovimientoDTO dto) {
        if (!perfilClient.existeJugador(jugadorId)) {
            throw new RuntimeException(jugadorId + " No existe");
        }

        Cartera cartera = carteraService.obtenerEntidad(jugadorId);

        int cambio = "INGRESO".equalsIgnoreCase(dto.getTipo()) ? dto.getMonto() : -dto.getMonto();
        int nuevoSaldo = cartera.getSaldo() + cambio;
        if (nuevoSaldo < 0) {
            throw new RuntimeException("Saldo insuficiente. Saldo actual: " + cartera.getSaldo());
        }

        cartera.setSaldo(nuevoSaldo);
        carteraService.guardar(cartera);

        Movimiento movimiento = new Movimiento();
        movimiento.setIdTransaccion(UUID.randomUUID().toString());
        movimiento.setCarteraId(cartera.getId());
        movimiento.setTipo(dto.getTipo());
        movimiento.setMonto(dto.getMonto());
        movimiento.setConcepto(dto.getConcepto());
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(dto.getTipo());
        movimiento.setDescripcion(dto.getConcepto());
        movimiento.setBilleterasIdBilletera("N/A");
        movimiento = movimientoRepository.save(movimiento);

        dto.setIdTransaccion(movimiento.getIdTransaccion());
        dto.setFecha(movimiento.getFecha());
        log.info("Movimiento registrado: {} para jugador {}", movimiento.getIdTransaccion(), jugadorId);
        return dto;
    }

    public List<MovimientoDTO> listarMovimientos(Long jugadorId) {
        Cartera cartera = carteraService.obtenerEntidad(jugadorId);
        return movimientoRepository.findByCarteraIdOrderByFechaDesc(cartera.getId())
                .stream()
                .map(m -> {
                    MovimientoDTO dto = new MovimientoDTO();
                    dto.setIdTransaccion(m.getIdTransaccion());
                    dto.setTipo(m.getTipo());
                    dto.setMonto(m.getMonto());
                    dto.setConcepto(m.getConcepto());
                    dto.setFecha(m.getFecha());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}