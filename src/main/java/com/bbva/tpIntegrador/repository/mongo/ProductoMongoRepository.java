package com.bbva.tpIntegrador.repository.mongo;

import com.bbva.tpIntegrador.model.mongo.ProductoDocumento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoMongoRepository extends MongoRepository<ProductoDocumento, String> {

    Optional<ProductoDocumento> findByEmail(String email);

    boolean existsByEmail(String email);
}
