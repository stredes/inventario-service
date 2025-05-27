package Inventario.Test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


// Data Transfer Object (DTO) para representar un producto
public class ProductoDTO {
    
    private String productId;    // ID único de MS Inventario
    private String name;         // Nombre compartido con MS Productos
    private String sku;          // SKU compartido con MS Productos

}
