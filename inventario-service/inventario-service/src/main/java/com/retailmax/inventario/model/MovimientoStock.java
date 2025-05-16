package com.retailmax.inventario.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // ENTRADA, SALIDA, RESERVA
    private String sku;
    private Integer cantidad;
    private String origen;
    private LocalDateTime fecha;
}
