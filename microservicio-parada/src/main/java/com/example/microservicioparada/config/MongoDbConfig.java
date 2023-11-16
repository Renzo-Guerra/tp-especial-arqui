package com.example.microservicioparada.config;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.example.microservicioparada.repositorios")
public class MongoDbConfig {

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString("mongodb://root:root@localhost:27017/prueba?authSource=admin");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "prueba");
    }
}

/**
 * The bean 'paradaMongoRepositorio',
 * defined in com.example.microservicioparada.repositorios.ParadaMongoRepositorio
 * defined in @EnableMongoRepositories declared on MicroservicioParadaApplication,
 * could not be registered. A bean with that name has already been
 * defined in com.example.microservicioparada.repositorios.ParadaMongoRepositorio
 * defined in @EnableMongoRepositories declared on MongoDbConfig and overriding is disabled.
 */