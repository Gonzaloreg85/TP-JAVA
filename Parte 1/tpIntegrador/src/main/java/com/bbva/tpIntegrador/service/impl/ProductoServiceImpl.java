package com.bbva.tpIntegrador.service.impl;

import com.bbva.tpIntegrador.dto.ProductoDto;
import com.bbva.tpIntegrador.exception.ProductoNotFoundException;
import com.bbva.tpIntegrador.model.ProductoDocumento;
import com.bbva.tpIntegrador.repository.ProductoMongoRepository;
import com.bbva.tpIntegrador.service.ProductosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductosService {
    @Autowired
    private ProductoMongoRepository productoRepository;
    
    @Override
    public ProductoDto crearProducto(ProductoDto productoDto) {
        ProductoDocumento producto = new ProductoDocumento ();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        

        ProductoDocumento savedCliente = productoRepository.save(producto);
        return convertirADto(savedCliente);
    }

    @Override
    public ProductoDto obtenerProducto(String id) {
        ProductoDocumento  producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
        return convertirADto(producto);
    }

      @Override
    public Page<ProductoDto> listarProductos(Pageable pageable) {
        return productoRepository.findAll(pageable)
                .map(this::convertirADto);
    }
    
    @Override
    public ProductoDto actualizarProducto(String id, ProductoDto productoDto) {
            ProductoDocumento producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
        
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());

        ProductoDocumento savedProducto = productoRepository.save(producto);
        return convertirADto(savedProducto);
    }
    
    @Override
    public void eliminarProducto(String id) {
        productoRepository.deleteById(id);
    }
    
    private ProductoDto convertirADto(ProductoDocumento producto) {
        ProductoDto dto = new ProductoDto ();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        
        return dto;
    }


}
