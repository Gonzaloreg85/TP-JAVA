package com.bbva.tpIntegrador.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "productos")
@Data
public class ProductoDocumento {
    @Id
    private String id;

    @Field("nombre_producto")
    private String nombre;

    @Field("precio")
    private double precio;

}







