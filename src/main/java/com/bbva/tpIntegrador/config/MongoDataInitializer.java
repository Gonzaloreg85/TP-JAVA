package com.bbva.tpIntegrador.config;
import com.bbva.tpIntegrador.model.mongo.ProductoDocumento;
import com.bbva.tpIntegrador.repository.mongo.ProductoMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MongoDataInitializer implements CommandLineRunner {
    
    private final ProductoMongoRepository productoRepository;


    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0) {
            log.info("Inicializando para MongoDB...");
            
            ProductoDocumento producto = new ProductoDocumento();
            producto.setNombre("Juan Pérez");
            producto.setPrecio(1300.00);


            productoRepository.save(producto);
            log.info("Producto creado con ID: {}", producto.getId());
        }
    }
}
