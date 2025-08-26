package com.bbva.tpIntegrador.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.bbva.tpIntegrador.repository.mongo")
public class MongoConfig {
    // Configuración específica para MongoDB
}
