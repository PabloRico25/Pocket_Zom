package com.example.compra.service;

import com.example.compra.client.BilleteraClient;
import com.example.compra.client.InventarioClient;
import com.example.compra.dto.AbrirSobreDTO;
import com.example.compra.dto.AperturaDTO;
import com.example.compra.model.Apertura;
import com.example.compra.model.Suministro;
import com.example.compra.repository.AperturaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class AperturaService {
    @Autowired
    private AperturaRepository aperturaRepository;
    @Autowired
    private SuministroService suministroService;
    @Autowired
    private BilleteraClient billeteraClient;
    @Autowired
    private InventarioClient inventarioClient;

    private Random random = new Random();
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    public AperturaDTO abrirSuministro(Long jugadorId, AbrirSobreDTO dto) {
        Suministro suministro = suministroService.obtenerEntidad(dto.getSuministroId());

        try {
            billeteraClient.registrarMovimiento(jugadorId, "EGRESO", suministro.getCosto(),
                    "Compra de " + suministro.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("No se pudo descontar el costo. Saldo insuficiente?");
        }

        List<String> cartasObtenidas = new ArrayList<>();
        for (int i = 0; i < suministro.getCantidadCartas(); i++) {
            String carta = generarCartaAleatoria();
            cartasObtenidas.add(carta);
        }

        Apertura apertura = new Apertura();
        apertura.setJugadorId(jugadorId);
        apertura.setSuministroId(suministro.getId());
        apertura.setCartasObtenidas(convertirListaAJson(cartasObtenidas));
        apertura = aperturaRepository.save(apertura);

        for (String carta : cartasObtenidas) {
            try {
                inventarioClient.agregarCarta(jugadorId, carta, 1);
            } catch (Exception e) {
                log.error("Error al añadir carta {} al inventario: {}", carta, e.getMessage());
            }
        }

        log.info("Apertura {} realizada: jugador={}, suministro={}, cartas={}",
                apertura.getId(), jugadorId, suministro.getNombre(), cartasObtenidas);
        return toDTO(apertura, suministro);
    }

    public List<AperturaDTO> listarAperturasPorJugador(Long jugadorId) {
        return aperturaRepository.findByJugadorId(jugadorId).stream()
                .map(a -> {
                    Suministro s = suministroService.obtenerEntidad(a.getSuministroId());
                    return toDTO(a, s);
                })
                .collect(Collectors.toList());
    }

    private String generarCartaAleatoria() {
        String[] cartas = {"ZMB-001", "ZMB-002", "HUM-001", "HUM-002", "BEST-001"};
        return cartas[random.nextInt(cartas.length)];
    }

    private String convertirListaAJson(List<String> lista) {
        try {
            return objectMapper.writeValueAsString(lista);
        } catch (JsonProcessingException e) {
            log.error("Error convirtiendo a JSON", e);
            return "[]";
        }
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
