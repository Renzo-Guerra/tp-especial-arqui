package com.example.microservicioparada.repositorios;

import com.example.microservicioparada.modelos.entidades.ParadaMongo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParadaMongoRepositorio extends MongoRepository<ParadaMongo, String> {

    @Query("""
            SELECT  p 
            FROM Parada p 
            WHERE p.latitud=:latitud AND p.longitud=:longitud 
            ORDER BY p.id_parada 
            LIMIT 1
            """)
    Optional<ParadaMongo> traerPorCoordenadas(Double latitud, Double longitud);
}
