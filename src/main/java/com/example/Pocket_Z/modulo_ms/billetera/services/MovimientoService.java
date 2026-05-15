package com.example.Pocket_Z.modulo_ms.billetera.services;

import com.example.Pocket_Z.modulo_ms.billetera.model.Cartera;
import com.example.Pocket_Z.modulo_ms.billetera.model.Movimiento;
import com.example.Pocket_Z.modulo_ms.billetera.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final CarteraService carteraService;

    @Transactional
    public Movimiento registrarMovimiento(Long jugadorId, String tipo, Integer monto, String concepto) {
        Cartera cartera = carteraService.obtenerPorJugador(jugadorId)
                .orElseThrow(() -> new RuntimeException("Cartera no encontrada"));

        // Actualizar saldo de la cartera
        int nuevoSaldo = cartera.getSaldo() + ("INGRESO".equals(tipo) ? monto : -monto);
        if (nuevoSaldo < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        cartera.setSaldo(nuevoSaldo);
        carteraService.guardar(cartera);

        // Crear movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setCartera(cartera);
        movimiento.setTipo(tipo);
        movimiento.setMonto(monto);
        movimiento.setConcepto(concepto);
        movimiento.setFecha(java.time.LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> listarMovimientos(Long jugadorId) {
        Cartera cartera = carteraService.obtenerPorJugador(jugadorId)
                .orElseThrow(() -> new RuntimeException("Cartera no encontrada"));
        return movimientoRepository.findByCarteraIdOrderByFechaDesc(cartera.getId());
    }
}