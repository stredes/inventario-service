package com.retailmax.inventario.service;

import com.retailmax.inventario.model.MovimientoStock;
import com.retailmax.inventario.model.Producto;
import com.retailmax.inventario.repository.MovimientoStockRepository;
import com.retailmax.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoStockService {

    private final MovimientoStockRepository movimientoStockRepository;
    private final ProductoRepository productoRepository;

    public MovimientoStockService(MovimientoStockRepository movimientoStockRepository, ProductoRepository productoRepository) {
        this.movimientoStockRepository = movimientoStockRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public void registrarMovimiento(String codigoProducto, String tipo, int cantidad) {
        Producto producto = productoRepository.findByCodigo(codigoProducto);

        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado: " + codigoProducto);
        }

        if ("SALIDA".equalsIgnoreCase(tipo) && producto.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente para realizar la salida.");
        }

        int nuevoStock = "ENTRADA".equalsIgnoreCase(tipo)
                ? producto.getStock() + cantidad
                : producto.getStock() - cantidad;

        producto.setStock(nuevoStock);
        productoRepository.save(producto);

        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setProducto(producto);
        movimiento.setTipo(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setFecha(LocalDateTime.now());

        movimientoStockRepository.save(movimiento);
    }

    public List<MovimientoStock> listarMovimientos() {
        return movimientoStockRepository.findAll();
    }
}
