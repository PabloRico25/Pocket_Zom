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
        if (suministro == null) {
            log.info("No se encontró el suministro con ID: " + dto.getSuministroId());
            return null;
        }
        boolean pagoExitoso = true;
        try {
            billeteraClient.registrarMovimiento(jugadorId, "EGRESO", suministro.getCosto(),
                    "Compra de " + suministro.getNombre());
            log.info("Se descontó " + suministro.getCosto() + " monedas al jugador " + jugadorId);
        } catch (Exception e) {
            log.info("Error al descontar el costo: " + e.getMessage());
            pagoExitoso = false;
        }
        if (!pagoExitoso) {
            log.info("No se pudo descontar el costo. Saldo insuficiente?");
            return null;
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
        Apertura guardada = aperturaRepository.save(apertura);
        for (String carta : cartasObtenidas) {
            try {
                inventarioClient.agregarCarta(jugadorId, carta, 1);
                log.info("Carta " + carta + " agregada al inventario del jugador " + jugadorId);
            } catch (Exception e) {
                log.info("Error al añadir carta " + carta + ": " + e.getMessage());
            }
        }
        log.info("Apertura " + guardada.getId() + " realizada: jugador=" + jugadorId +
                ", suministro=" + suministro.getNombre() + ", cartas=" + cartasObtenidas);
        return toDTO(guardada, suministro);
    }

    public List<AperturaDTO> listarAperturasPorJugador(Long jugadorId) {
        List<Apertura> aperturas = aperturaRepository.findByJugadorId(jugadorId);
        List<AperturaDTO> resultado = new ArrayList<>();
        for (Apertura apertura : aperturas) {
            Suministro suministro = suministroService.obtenerEntidad(apertura.getSuministroId());
            if (suministro == null) {
                log.info("No se encontró el suministro con ID: " + apertura.getSuministroId());
                continue;
            }
            AperturaDTO dto = toDTO(apertura, suministro);
            resultado.add(dto);
        }
        return resultado;
    }

    private String generarCartaAleatoria() {
        String[] cartas = {"ZMB-001", "ZMB-002", "HUM-001", "HUM-002", "BEST-001"};
        return cartas[random.nextInt(cartas.length)];
    }

    private String convertirListaAJson(List<String> lista) {
        if (lista == null) {
            log.info("La lista es null, devolviendo JSON vacío");
            return "[]";
        }
        try {
            String json = objectMapper.writeValueAsString(lista);
            return json;
        } catch (Exception e) {
            log.info("Error al convertir la lista a JSON: " + e.getMessage());
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
