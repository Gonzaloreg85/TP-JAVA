package com.bbva.tpIntegrador.controller;

import com.bbva.tpIntegrador.dto.ProductoDto;
import com.bbva.tpIntegrador.service.ProductosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductosService productoService;


    @PostMapping
    public ResponseEntity<ProductoDto> crearProducto(@Valid @RequestBody ProductoDto productoDto) {
        ProductoDto productoCreado = productoService.crearProducto(productoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtenerProducto(@PathVariable String id) {
        ProductoDto producto = productoService.obtenerProducto(id);
        return ResponseEntity.ok(producto);
    }
    
    @GetMapping
    public ResponseEntity<Page<ProductoDto>> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductoDto> producto = productoService.listarProductos(pageable);
        return ResponseEntity.ok(producto);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(
            @PathVariable String id,
            @Valid @RequestBody ProductoDto ProductoDto) {
        ProductoDto ProductoActualizado = productoService.actualizarProducto(id, ProductoDto);
        return ResponseEntity.ok(ProductoActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/crearproducto/async")
    //* Enpoint para crear productos de manera asincronica */
    public ResponseEntity<?> crearProductosBatch(@RequestBody List<ProductoDto> productos) {
        if (productos.size() > 10) {
            //* Validación para no permitir más de 10 productos a la vez
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Solo se pueden cargar hasta 10 productos a la vez.");
        }
        //* Llamada asíncrona
        return ResponseEntity.accepted().body(productoService.crearProductosAsync(productos));
    }

    @PostMapping("/crearproducto/sync")
    //* Enpoint para crear productos de manera sincrona */
    public ResponseEntity<?> crearProductosBatchSync(@RequestBody List<ProductoDto> productos) {
        if (productos.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Solo se pueden cargar hasta 10 productos a la vez.");
        }
        //* Llamada síncrona *//
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProductosSync(productos));
    }
}
