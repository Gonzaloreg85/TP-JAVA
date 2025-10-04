package com.bbva.tpIntegrador.repository;

import com.bbva.tpIntegrador.model.ProductoDocumento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoMongoRepository extends MongoRepository<ProductoDocumento, String> {


}
