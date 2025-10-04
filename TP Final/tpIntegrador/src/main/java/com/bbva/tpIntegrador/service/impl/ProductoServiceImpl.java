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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductosService {
    @Autowired
    private ProductoMongoRepository productoRepository;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

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
    public boolean eliminarProducto(String id) {
        productoRepository.deleteById(id);
        return false;
    }
    @Override
    //* Indico que este método es asíncrono
    @Async
    public CompletableFuture<List<ProductoDto>> crearProductosAsync(List<ProductoDto> productos) {
        List<CompletableFuture<ProductoDto>> futures = productos.stream()
                .map(productoDto -> CompletableFuture.supplyAsync(() -> {
                    //* Registro el tiempo de inicio
                    long start = System.currentTimeMillis();
                    ProductoDto creado = crearProducto(productoDto);
                    long end = System.currentTimeMillis();
                    //* Obtengo el nombrel del hilo actual para mostrar en el log
                    String threadName = Thread.currentThread().getName();
                    //* Calculo la duración
                    long duration = end - start;
                    //* muestro en consola el nombre del hilo y el tiempo que tardó en crear el producto
                    System.out.println("[" + threadName + "] Producto cargado Asincronicamente: " + creado.getNombre() + ", duración: " + duration + " ms");
                    //* Devuelvo el producto creado
                    return creado;
                }, executorService))
                .collect(Collectors.toList());
        //* Espero a que todas las tareas asíncronas terminen y luego recopilo los resultados
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }
    //* Metodo Sincrono para crear productos
    @Override
    public List<ProductoDto> crearProductosSync(List<ProductoDto> productos) {
        return productos.stream()
                .map(productoDto -> {
                    //* Registro el tiempo de inicio
                    long start = System.currentTimeMillis();
                    ProductoDto creado = crearProducto(productoDto);
                    //* Registro el tiempo de fin
                    long end = System.currentTimeMillis();
                    //* Obtengo el nombre del hilo actual
                    String threadName = Thread.currentThread().getName();
                    //*
                    long duration = end - start;
                    //* Muestro en consola el nombre del hilo y el tiempo que tardó en crear el producto
                    System.out.println("[" + threadName + "] Producto cargado Sincronicamente: " + creado.getNombre() + ", duración: " + duration + " ms");
                    return creado;
                })
                .collect(Collectors.toList());
    }

    private ProductoDto convertirADto(ProductoDocumento producto) {
        ProductoDto dto = new ProductoDto ();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        
        return dto;
    }


}
