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
        // 1. Validar que el jugador existe (Feign)
        if (!perfilClient.existeJugador(jugadorId)) {
            throw new RuntimeException("El jugador " + jugadorId + " no existe");
        }

        // 2. Obtener la cartera
        Cartera cartera = carteraService.obtenerEntidad(jugadorId);

        // 3. Calcular nuevo saldo
        int cambio = "INGRESO".equalsIgnoreCase(dto.getTipo()) ? dto.getMonto() : -dto.getMonto();
        int nuevoSaldo = cartera.getSaldo() + cambio;
        if (nuevoSaldo < 0) {
            throw new RuntimeException("Saldo insuficiente. Saldo actual: " + cartera.getSaldo());
        }

        // 4. Actualizar saldo de la cartera
        cartera.setSaldo(nuevoSaldo);
        carteraService.guardar(cartera);

        // 5. Crear y guardar el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setIdTransaccion(UUID.randomUUID().toString());
        movimiento.setCarteraId(cartera.getId());
        movimiento.setTipo(dto.getTipo());
        movimiento.setMonto(dto.getMonto());
        movimiento.setConcepto(dto.getConcepto());
        movimiento.setFecha(LocalDateTime.now());

        // ** Asignar valores a las columnas extra de la tabla **
        movimiento.setTipoMovimiento(dto.getTipo());            // valor igual al tipo
        movimiento.setDescripcion(dto.getConcepto());           // usar el concepto como descripción
        movimiento.setBilleterasIdBilletera("N/A");             // valor por defecto

        movimiento = movimientoRepository.save(movimiento);

        // 6. Completar el DTO con los datos generados
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