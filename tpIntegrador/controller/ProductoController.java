// ...existing code...
    @PostMapping("/batch")
    public ResponseEntity<?> crearProductosBatch(@Valid @RequestBody List<ProductoDto> productos) {
        if (productos.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Solo se pueden cargar hasta 10 productos a la vez.");
        }
        // Llamada asíncrona
        return ResponseEntity.accepted().body(productoService.crearProductosAsync(productos));
    }
// ...existing code...

