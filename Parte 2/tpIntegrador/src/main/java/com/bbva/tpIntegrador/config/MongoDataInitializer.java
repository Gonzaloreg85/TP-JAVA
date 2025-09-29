package com.bbva.tpIntegrador.config;
import com.bbva.tpIntegrador.model.ProductoDocumento;
import com.bbva.tpIntegrador.repository.ProductoMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MongoDataInitializer implements CommandLineRunner {
    @Autowired
    private final ProductoMongoRepository productoRepository;


    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0) {

            
            ProductoDocumento producto = new ProductoDocumento();
            producto.setNombre("Juan Pérez");
            producto.setPrecio(1300.00);


            productoRepository.save(producto);

        }
    }
}
