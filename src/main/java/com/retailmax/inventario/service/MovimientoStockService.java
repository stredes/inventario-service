package com.retailmax.inventario.service;

import com.retailmax.inventario.entity.MovimientoStock;
import com.retailmax.inventario.entity.Producto;
import com.retailmax.inventario.repository.MovimientoStockRepository;
import com.retailmax.inventario.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoStockService {

    private final MovimientoStockRepository movimientoRepo;
    private final ProductoRepository productoRepo;

    public MovimientoStock registrarMovimiento(Long productoId, Integer cantidad, String tipo, String observacion) {
        Producto producto = productoRepo.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (tipo.equalsIgnoreCase("SALIDA") && producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        if (tipo.equalsIgnoreCase("ENTRADA")) {
            producto.setStock(producto.getStock() + cantidad);
        } else if (tipo.equalsIgnoreCase("SALIDA")) {
            producto.setStock(producto.getStock() - cantidad);
        }

        productoRepo.save(producto);

        MovimientoStock mov = MovimientoStock.builder()
                .producto(producto)
                .cantidad(cantidad)
                .tipo(tipo.toUpperCase())
                .fecha(LocalDateTime.now())
                .observacion(observacion)
                .build();

        return movimientoRepo.save(mov);
    }

    public List<MovimientoStock> listarMovimientos() {
        return movimientoRepo.findAll();
    }
}
