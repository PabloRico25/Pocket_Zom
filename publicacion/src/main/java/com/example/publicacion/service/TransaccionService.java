package com.example.publicacion.service;

import com.example.publicacion.dto.TransaccionResponseDTO;
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

    @Transactional
    public TransaccionResponseDTO comprar(Long compradorId, Long publicacionId) {
        // Marcar publicación como vendida
        var publicacion = publicacionService.marcarComoVendida(publicacionId);
        // Crear transacción
        Transaccion t = new Transaccion();
        t.setPublicacion(publicacion);
        t.setCompradorId(compradorId);
        t.setFechaCompra(LocalDateTime.now());
        t = transaccionRepository.save(t);
        log.info("Compra realizada: publicación {} por comprador {}", publicacionId, compradorId);
        return convertirADTO(t);
    }

    public List<TransaccionResponseDTO> listarComprasPorComprador(Long compradorId) {
        return transaccionRepository.findByCompradorId(compradorId)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<TransaccionResponseDTO> listarVentasPorVendedor(Long vendedorId) {
        return transaccionRepository.findByPublicacion_VendedorId(vendedorId)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    private TransaccionResponseDTO convertirADTO(Transaccion t) {
        return new TransaccionResponseDTO(
                t.getId(),
                t.getPublicacion().getId(),
                t.getPublicacion().getCodigoCarta(),
                t.getPublicacion().getPrecio(),
                t.getPublicacion().getVendedorId(),
                t.getCompradorId(),
                t.getFechaCompra()
        );
    }
}