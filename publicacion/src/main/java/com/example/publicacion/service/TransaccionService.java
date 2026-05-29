package com.example.publicacion.service;

import com.example.publicacion.cliente.BilleteraCliente;
import com.example.publicacion.cliente.InventarioCliente;
import com.example.publicacion.dto.MovimientoDTO;
import com.example.publicacion.dto.TransaccionDTO;
import com.example.publicacion.dto.TransferirCartaDTO;
import com.example.publicacion.model.Publicacion;
import com.example.publicacion.model.Transaccion;
import com.example.publicacion.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final PublicacionService publicacionService;
    private final BilleteraCliente billeteraCliente;
    private final InventarioCliente inventarioCliente;

    @Transactional
    public TransaccionDTO comprar(Long compradorId, Long publicacionId) {
        Publicacion publicacion = publicacionService.marcarComoVendida(publicacionId);
        if (publicacion == null) {
            log.warn("No se pudo marcar como vendida la publicación {}", publicacionId);
            return null;
        }

        Transaccion t = new Transaccion();
        t.setPublicacionId(publicacionId);
        t.setCompradorId(compradorId);
        t.setFechaCompra(LocalDateTime.now());
        t = transaccionRepository.save(t);

        try {
            inventarioCliente.transferirCarta(
                    new TransferirCartaDTO(
                            publicacion.getVendedorId(),
                            compradorId,
                            publicacion.getCodigoCarta(),
                            1
                    )
            );
        } catch (Exception e) {
            log.error("Error al transferir carta: {}", e.getMessage());
            return null;
        }

        try {
            billeteraCliente.registrarMovimiento(
                    compradorId,
                    new MovimientoDTO("EGRESO", publicacion.getPrecio(),
                            "Compra carta " + publicacion.getCodigoCarta())
            );
            billeteraCliente.registrarMovimiento(
                    publicacion.getVendedorId(),
                    new MovimientoDTO("INGRESO", publicacion.getPrecio(),
                            "Venta carta " + publicacion.getCodigoCarta())
            );
        } catch (Exception e) {
            log.error("Error en movimientos de billetera: {}", e.getMessage());
        }

        log.info("Compra realizada: publicacion={}, comprador={}", publicacionId, compradorId);
        return toDTO(t);
    }

    public List<TransaccionDTO> listarPorComprador(Long compradorId) {
        return transaccionRepository.findByCompradorId(compradorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private TransaccionDTO toDTO(Transaccion t) {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setId(t.getId());
        dto.setPublicacionId(t.getPublicacionId());
        dto.setCompradorId(t.getCompradorId());
        dto.setFechaCompra(t.getFechaCompra());
        return dto;
    }
}
