package com.example.microserviciomonopatin.repositorios;

import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonopatinRepositorio extends CrudRepository<Monopatin, Long> {

}
