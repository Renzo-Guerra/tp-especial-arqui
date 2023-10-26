package com.example.microserviciomonopatin.repositorios;

import com.example.microserviciomonopatin.modelos.entidades.Viaje;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepositorio extends CrudRepository<Viaje, Long> {


}
