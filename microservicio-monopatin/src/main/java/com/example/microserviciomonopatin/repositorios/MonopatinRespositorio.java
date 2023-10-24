package com.example.microserviciomonopatin.repositorios;

import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface MonopatinRespositorio extends CrudRepository<Monopatin, Long> {

}
