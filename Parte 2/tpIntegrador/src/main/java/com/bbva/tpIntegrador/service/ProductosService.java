package com.bbva.tpIntegrador.service;

import com.bbva.tpIntegrador.dto.ProductoDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductosService {
    ProductoDto crearProducto(ProductoDto productoDto);

    ProductoDto obtenerProducto(String id);

    Page<ProductoDto> listarProductos(Pageable pageable);

    ProductoDto actualizarProducto(String id, ProductoDto productoDto);

    void eliminarProducto(String id);

    Object crearProductosAsync(@Valid List<ProductoDto> productos);

    Object crearProductosSync(List<ProductoDto> productos);
}
