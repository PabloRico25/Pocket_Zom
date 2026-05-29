package com.example.billetera.service;

import com.example.billetera.cliente.PerfilCliente;
import com.example.billetera.model.Cartera;
import com.example.billetera.model.Movimiento;
import com.example.billetera.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CarteraService carteraService;
    private final PerfilCliente perfilClient;

    public List<Movimiento> listarPorJugador(Long idJugador) {
        Cartera cartera = carteraService.buscarPorJugador(idJugador);
        if (cartera == null) {
            log.warn("No existe cartera para el jugador {}", idJugador);
            return null;
        }
        return movimientoRepository.findByIdCarteraOrderByFechaDesc(cartera.getIdCartera());
    }

    public Movimiento registrar(Long idJugador, String tipo, Integer monto, String concepto) {
        if (!Boolean.TRUE.equals(perfilClient.existeJugador(idJugador))) {
            log.warn("Jugador {} no existe en perfil", idJugador);
            return null;
        }
        Cartera cartera = carteraService.buscarPorJugador(idJugador);
        if (cartera == null) {
            log.warn("No existe cartera para el jugador {}", idJugador);
            return null;
        }
        int cambio = "INGRESO".equalsIgnoreCase(tipo) ? monto : -monto;
        int nuevoSaldo = cartera.getSaldo() + cambio;
        if (nuevoSaldo < 0) {
            log.warn("Saldo insuficiente para jugador {}. Saldo actual: {}", idJugador, cartera.getSaldo());
            return null;
        }
        cartera.setSaldo(nuevoSaldo);
        carteraService.guardar(cartera);

        Movimiento movimiento = new Movimiento();
        movimiento.setIdCartera(cartera.getIdCartera());
        movimiento.setTipo(tipo.toUpperCase());
        movimiento.setMonto(monto);
        movimiento.setConcepto(concepto);
        movimiento.setFecha(LocalDateTime.now());

        Movimiento guardado = movimientoRepository.save(movimiento);
        log.info("Movimiento {} registrado para jugador {}", guardado.getIdTransaccion(), idJugador);
        return guardado;
    }
}
