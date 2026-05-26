package com.example.billetera.service;

import com.example.billetera.dto.MovimientoRequestDTO;
import com.example.billetera.dto.MovimientoResponseDTO;
import com.example.billetera.model.Cartera;
import com.example.billetera.model.Movimiento;
import com.example.billetera.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private CarteraService carteraService;

    @Transactional
    public Movimiento registrarMovimiento(Long jugadorId, String tipo, Integer monto, String concepto) {
        log.info("Registrando movimiento para jugador: " + jugadorId);
        Cartera cartera = carteraService.obtenerPorJugador(jugadorId);
        if (cartera == null) {
            log.info("Cartera no encontrada para jugador: " + jugadorId);
            return null;
        }
        int nuevoSaldo = cartera.getSaldo();
        if ("INGRESO".equalsIgnoreCase(tipo)) {
            nuevoSaldo = cartera.getSaldo() + monto;
        } else if ("EGRESO".equalsIgnoreCase(tipo)) {
            nuevoSaldo = cartera.getSaldo() - monto;
        } else {
            log.info("Tipo inválido: " + tipo);
            return null;
        }
        if (nuevoSaldo < 0) {
            log.info("Saldo insuficiente. Saldo actual: " + cartera.getSaldo());
            return null;
        }
        cartera.setSaldo(nuevoSaldo);
        carteraService.guardar(cartera);
        Movimiento mov = new Movimiento();
        mov.setIdTransaccion(UUID.randomUUID().toString());
        mov.setCartera(cartera);
        mov.setTipo(tipo.toUpperCase());
        mov.setMonto(monto);
        mov.setConcepto(concepto);
        mov.setFecha(LocalDateTime.now());

        Movimiento guardado = movimientoRepository.save(mov);
        log.info("Movimiento registrado con ID: " + guardado.getIdTransaccion());

        return guardado;
    }

    public List<Movimiento> listarMovimientos(Long jugadorId) {
        Cartera cartera = carteraService.obtenerPorJugador(jugadorId);
        if (cartera == null) {
            return new ArrayList<>();
        }

        List<Movimiento> movimientos = movimientoRepository.findByCarteraIdOrderByFechaDesc(cartera.getId());
        if (movimientos == null) {
            return new ArrayList<>();
        }

        return movimientos;
    }
}