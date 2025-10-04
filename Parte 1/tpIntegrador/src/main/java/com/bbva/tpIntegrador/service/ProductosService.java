package com.bbva.tpIntegrador.service;

import com.bbva.tpIntegrador.dto.ProductoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductosService {
    ProductoDto crearProducto(ProductoDto productoDto);

    ProductoDto obtenerProducto(String id);

    Page<ProductoDto> listarProductos(Pageable pageable);

    ProductoDto actualizarProducto(String id, ProductoDto productoDto);

    void eliminarProducto(String id);
}
