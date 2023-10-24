package com.example.microservicioviaje.repositorios;

import com.example.microservicioviaje.entidades.Viaje;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepositorio extends CrudRepository<Viaje, Long> {


}
