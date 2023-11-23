package com.example.microservicioparada.repositorios;

import com.example.microservicioparada.modelos.entidades.ParadaMongo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParadaMongoRepositorio extends MongoRepository<ParadaMongo, String> {

    @Query("{'latitud': ?0, 'longitud': ?1}")
    List<ParadaMongo> traerPorCoordenadas(Double latitud, Double longitud, Pageable pageable);
}
