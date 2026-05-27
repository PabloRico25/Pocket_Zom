package com.example.publicacion.service;

import com.example.publicacion.client.BilleteraClient;
import com.example.publicacion.client.InventarioClient;
import com.example.publicacion.dto.TransaccionDTO;
import com.example.publicacion.model.Publicacion;
import com.example.publicacion.model.Transaccion;
import com.example.publicacion.repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TransaccionService {
    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private PublicacionService publicacionService;
    @Autowired
    private BilleteraClient billeteraClient;
    @Autowired
    private InventarioClient inventarioClient;
    public TransaccionDTO comprar(Long compradorId, Long publicacionId) {
        Publicacion publicacion = publicacionService.marcarComoVendida(publicacionId);
        Transaccion t = new Transaccion();
        t.setPublicacionId(publicacionId);
        t.setCompradorId(compradorId);
        t.setFechaCompra(LocalDateTime.now());
        t = transaccionRepository.save(t);
        try {
            inventarioClient.transferirCarta(publicacion.getVendedorId(), compradorId,
                    publicacion.getCodigoCarta(), 1);
        } catch (Exception e) {
            log.error("Error al transferir carta: {}", e.getMessage());
            throw new RuntimeException("No se pudo transferir la carta", e);
        }
        try {
            billeteraClient.registrarMovimiento(compradorId, "EGRESO", publicacion.getPrecio(),
                    "Compra de carta " + publicacion.getCodigoCarta());
            billeteraClient.registrarMovimiento(publicacion.getVendedorId(), "INGRESO", publicacion.getPrecio(),
                    "Venta de carta " + publicacion.getCodigoCarta());
        } catch (Exception e) {
            log.error("Error al registrar movimientos en billetera: {}", e.getMessage());
            throw new RuntimeException("Error en la transacción de monedas", e);
        }
        return toDTO(t, publicacion);
    }
    public List<TransaccionDTO> listarComprasPorComprador(Long compradorId) {
        return transaccionRepository.findByCompradorId(compradorId).stream()
                .map(t -> toDTO(t, null)).collect(java.util.stream.Collectors.toList());
    }
    public List<TransaccionDTO> listarVentasPorVendedor(Long vendedorId) {
        List<Publicacion> publicaciones = publicacionService.listarPorVendedor(vendedorId).stream()
                .map(dto -> {
                    Publicacion p = new Publicacion();
                    p.setId(dto.getId());
                    return p;
                }).collect(java.util.stream.Collectors.toList());
        return publicaciones.stream()
                .flatMap(p -> transaccionRepository.findByPublicacionId(p.getId()).stream())
                .map(t -> toDTO(t, null))
                .collect(java.util.stream.Collectors.toList());
    }
    private TransaccionDTO toDTO(Transaccion t, Publicacion p) {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setId(t.getId());
        dto.setPublicacionId(t.getPublicacionId());
        dto.setCompradorId(t.getCompradorId());
        dto.setFechaCompra(t.getFechaCompra());
        return dto;
    }
}
