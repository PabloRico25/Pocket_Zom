package com.example.compra.service;

import com.example.compra.cliente.BilleteraCliente;
import com.example.compra.cliente.InventarioCliente;
import com.example.compra.dto.AbrirSobreDTO;
import com.example.compra.dto.AperturaDTO;
import com.example.compra.dto.MovimientoDTO;
import com.example.compra.model.Apertura;
import com.example.compra.model.Suministro;
import com.example.compra.repository.AperturaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AperturaService {

    private final AperturaRepository aperturaRepository;
    private final SuministroService suministroService;
    private final BilleteraCliente billeteraCliente;
    private final InventarioCliente inventarioCliente;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    public AperturaDTO abrir(Long idJugador, AbrirSobreDTO dto) {
        Suministro suministro = suministroService.obtenerEntidad(dto.getSuministroId());
        if (suministro == null) {
            log.warn("Suministro no encontrado: {}", dto.getSuministroId());
            return null;
        }
        try {
            billeteraCliente.registrarMovimiento(idJugador,
                    new MovimientoDTO("EGRESO", suministro.getCosto(),
                            "Compra de " + suministro.getNombre()));
        } catch (Exception e) {
            log.error("Error al descontar costo: {}", e.getMessage());
            return null;
        }
        List<String> cartasObtenidas = new ArrayList<>();
        for (int i = 0; i < suministro.getCantidadCartas(); i++) {
            cartasObtenidas.add(generarCartaAleatoria());
        }
        Apertura apertura = new Apertura();
        apertura.setJugadorId(idJugador);
        apertura.setSuministroId(suministro.getId());
        apertura.setCartasObtenidas(convertirAJson(cartasObtenidas));
        apertura = aperturaRepository.save(apertura);

        for (String carta : cartasObtenidas) {
            try {
                inventarioCliente.agregarCarta(idJugador, carta, 1);
            } catch (Exception e) {
                log.error("Error al agregar carta {} al inventario: {}", carta, e.getMessage());
            }
        }
        log.info("Apertura {} realizada para jugador {}", apertura.getId(), idJugador);
        return toDTO(apertura, suministro);
    }

    public List<AperturaDTO> listarPorJugador(Long idJugador) {
        return aperturaRepository.findByJugadorId(idJugador).stream()
                .map(a -> toDTO(a, suministroService.obtenerEntidad(a.getSuministroId())))
                .collect(Collectors.toList());
    }

    private String generarCartaAleatoria() {
        String[] cartas = {"ZMB-001", "ZMB-002", "HUM-001", "HUM-002", "BEST-001"};
        return cartas[random.nextInt(cartas.length)];
    }

    private String convertirAJson(List<String> lista) {
        try { return objectMapper.writeValueAsString(lista); } catch (Exception e) { return "[]"; }
    }

    private AperturaDTO toDTO(Apertura a, Suministro s) {
        AperturaDTO dto = new AperturaDTO();
        dto.setId(a.getId());
        dto.setJugadorId(a.getJugadorId());
        dto.setSuministroId(a.getSuministroId());
        dto.setSuministroNombre(s != null ? s.getNombre() : "");
        dto.setFecha(a.getFecha());
        dto.setCartasObtenidas(a.getCartasObtenidas());
        return dto;
    }
}
