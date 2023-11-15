package com.example.microservicioparada.repositorios;

import com.example.microservicioparada.modelos.entidades.ParadaMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParadaMongoRepositorio extends MongoRepository<ParadaMongo, String> {


}
